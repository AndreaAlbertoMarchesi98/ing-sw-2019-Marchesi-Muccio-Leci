package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class VirtualView implements Runnable, View {

    private Player player;
    private final Server server;
    private GameController gameController;
    private final PingSender pingSender;
    private final ObjectInputStream input;
    private final ObjectOutputStream output;
    private boolean running;

    private static class PingSender implements Runnable {
        private final VirtualView virtualView;

        public PingSender(VirtualView virtualView) {
            this.virtualView = virtualView;
        }

        @Override
        public void run() {
            while (virtualView.isRunning()) {
                try {
                    virtualView.sendMessage(new ServerPing());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public class MessageReceiver implements Runnable {
        private final Object message;

        public MessageReceiver(Object message) {
            this.message = message;
        }

        public void run() {
            if (message instanceof AuthenticationMessage)
                notifyNickname((AuthenticationMessage) message);

            if (gameController != null) {

                if (message instanceof GameSetUpMessage)
                    notifyGameSetUp((GameSetUpMessage) message);

                else if (message instanceof CardMessage)
                    notifyCard((CardMessage) message);

                else if (message instanceof SelectedTileMessage)
                    notifySelectedTile((SelectedTileMessage) message);

                else if (message instanceof PowerActiveMessage)
                    notifyIsPowerActive((PowerActiveMessage) message);

                else if (message instanceof UndoMessage)
                    notifyUndo((UndoMessage) message);

                else if (message instanceof ClientDisconnectionMessage)
                    notifyDisconnection();

                else if (message instanceof RestartMessage)
                    notifyRestart();
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void stop() {
        System.out.println("client disconnected, virtualView has stopped");
        running = false;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public GameController getGameController() {
        return gameController;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public VirtualView(Socket client, Server server) throws IOException {
        running = true;

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
        }
    }


    private void processMessages() throws SocketTimeoutException, IOException, ClassNotFoundException {
        while (isRunning()) {
            Object message = input.readObject();

            new Thread(new MessageReceiver(message)).start();
        }
    }

    private synchronized void notifyDisconnection() {
        stop();
        server.handleDisconnection(this);
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

    public void notifyDisconnection(ClientDisconnectionMessage clientDisconnectionMessage) {
        //gameController.no(gameSetUpMessage);
    }

    public void notifyRestart() {
        gameController.restartView(this);
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

    public void updateBoard(BoardMessage boardMessage) {
        sendMessage(boardMessage);
    }

    public void updateDefeat(NoMovesMessage noMovesMessage) {
        sendMessage(noMovesMessage);
    }

    public void updateVictory(VictoryMessage victoryMessage) {
        sendMessage(victoryMessage);
    }

    public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {
        sendMessage(disconnectionMessage);
    }

    private synchronized void sendMessage(Object message) {
        try {
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
