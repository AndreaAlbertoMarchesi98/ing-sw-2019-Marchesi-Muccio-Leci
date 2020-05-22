package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.view.ActionNames;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class VirtualView implements Runnable, View {

    private Player player;
    private Server server;
    private GameController gameController;
    private PingSender pingSender;
    private ObjectInputStream input;
    private ObjectOutputStream output;

    private static class PingSender implements Runnable{
        private final VirtualView virtualView;
        private boolean isRunning;

        public PingSender(VirtualView virtualView){
            this.virtualView = virtualView;
        }
        @Override
        public void run() {
            while (isRunning) {
                try {
                    virtualView.sendMessage(new ServerPing());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        public void stop() {
            isRunning = false;
        }
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public VirtualView(Socket client, Server server) throws IOException {
        this.server = server;

        input = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());

        pingSender = new PingSender(this);
        Thread pingSenderThread = new Thread(pingSender);
        pingSenderThread.start();
    }

    @Override
    public void run() {
        try {
            processMessages();
        } catch (SocketTimeoutException e) {
            System.out.println("it s timeout!!!!!!!!!!!!");
            notifyDisconnection();
        } catch (IOException | ClassNotFoundException e) {
            notifyDisconnection();
            //e.printStackTrace();
        }
    }


    private void processMessages() throws SocketTimeoutException, IOException, ClassNotFoundException {
        while (true) {
            Object message = input.readObject();

            if (message instanceof AuthenticationMessage)
                notifyNickname((AuthenticationMessage) message);

            else if (message instanceof GameSetUpMessage)
                notifyGameSetUp((GameSetUpMessage) message);

            else if (message instanceof CardMessage)
                notifyCard((CardMessage) message);

            else if (message instanceof SelectedTileMessage)
                notifySelectedTile((SelectedTileMessage) message);

            else if (message instanceof PowerActiveMessage)
                notifyIsPowerActive((PowerActiveMessage) message);

            else if (message instanceof DisconnectionMessage)
                notifyDisconnection();

            else if (message instanceof UndoMessage)
                notifyUndo((UndoMessage) message);


        }
    }

    private void notifyDisconnection() {
        synchronized (this) {
            System.out.println("client disconnected");
            pingSender.stop();
            if (gameController != null)
                gameController.handleDisconnection(this);
            else
                server.handleDisconnection(this);
        }
    }

    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        String nickname = authenticationMessage.nickname;
        server.tryAuthenticatingView(nickname, this);
    }

    public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage) {
        gameController.createLobby(gameSetUpMessage, this);
    }

    public void notifyCard(CardMessage cardMessage) {
        gameController.setPlayerCard(cardMessage, this);
    }

    public void notifySelectedTile(SelectedTileMessage selectedTileMessage) {
        gameController.processSelection(selectedTileMessage, player);
    }

    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {
        gameController.setPowerActive();
    }

    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {
        //gameController.no(gameSetUpMessage);
    }

    public void notifyUndo(UndoMessage undoMessage) {
        gameController.receiveUndo();
    }

    public void updateGameCreation() {
        sendMessage(new GameCreationMessage());
    }

    public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {
        sendMessage(invalidNameMessage);
    }

    public void updateLobby(LobbyMessage lobbyMessage) {
        sendMessage(lobbyMessage);
    }

    public void updateWaitingRoom(WaitMessage waitMessage) {
        sendMessage(waitMessage);
    }

    public void updateBoard(BoardMessage boardMessage) {
        sendMessage(boardMessage);
    }

    public void updateVictory(VictoryMessage victoryMessage) {
        sendMessage(victoryMessage);
    }

    public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {
        sendMessage(disconnectionMessage);
    }

    private void sendMessage(Object message) {
        synchronized (this) {
            try {
                output.writeObject(message);
                output.reset();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
