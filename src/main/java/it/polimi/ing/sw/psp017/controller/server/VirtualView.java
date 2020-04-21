package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.RemainingCardsMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.view.GodName;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Queue;

public class VirtualView implements Runnable {

    private Player player;
    private boolean isConnected;
    private boolean isAuthenticated;
    private Server server;
    private Socket client;
    private GameController gameController;
    private boolean hasJoinedLobby;
    private Queue<Object> messages;
    ObjectInputStream input;
    ObjectOutputStream output;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public VirtualView(Socket client, Server server) throws IOException {
        this.client = client;
        this.isAuthenticated = false;
        this.server = server;
        input = new ObjectInputStream(client.getInputStream());
        output = new ObjectOutputStream(client.getOutputStream());
    }

    @Override
    public void run() {
        try {
            authenticate();

            processMessages();
        } catch (SocketTimeoutException e) {
            notifyDisconnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void authenticate() throws IOException, ClassNotFoundException {
        Object obj;
        while (!isAuthenticated) {
            obj = input.readObject();
            if (obj instanceof AuthenticationMessage) {
                String nickname = ((AuthenticationMessage) obj).nickname;
                if (server.tryAuthenticatingView(nickname, this)) {
                    isAuthenticated = true;
                } else {
                    //username already taken
                }
            }
        }
    }

    private void processMessages() throws IOException, ClassNotFoundException {
        while (true) {
            Object message = input.readObject();

            if (message instanceof GameSetUpMessage) {
                GameSetUpMessage gc = ((GameSetUpMessage) message);
                gameController.createLobby(this, gc.godNames);
            } else if (message instanceof CardMessage) {
                CardMessage cc = ((CardMessage) message);
                gameController.setPlayerCard(cc.godName, this);
            } else if (message instanceof SelectionMessage) {
                SelectionMessage sm = ((SelectionMessage) message);
                gameController.calculateValidTiles(sm.workerTile, sm.isPowerActive, this);
            } else if (message instanceof ActionMessage) {
                ActionMessage am = ((ActionMessage) message);
                //gameController.performAction();
            } else if (message instanceof DisconnectionMessage) {
                notifyDisconnection();
            }

        }
    }

    private void notifyDisconnection() {
        if (gameController != null)
            gameController.handleDisconnection(this);
        else
            server.handleDisconnection(this);
    }

    public void updateLobby(ArrayList<String> players, ArrayList<GodName> cards) {
        LobbyMessage npm = new LobbyMessage(players, cards);
        try {
            output.writeObject(npm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateCardChoices(ArrayList<GodName> card) {
        RemainingCardsMessage ccm = new RemainingCardsMessage(card);
        try {
            output.writeObject(ccm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateValidTiles(boolean[][] validTiles) {

    }

    public void updateBoard(Board board) {

    }
}
