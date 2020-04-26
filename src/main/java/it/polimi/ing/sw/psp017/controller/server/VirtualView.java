package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Queue;

public class VirtualView implements Runnable, View {

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
            processMessages();
        } catch (SocketTimeoutException e) {
            notifyDisconnection();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void processMessages() throws IOException, ClassNotFoundException {
        while (true) {
            Object message = input.readObject();

            if (message instanceof AuthenticationMessage)
                notifyNickname((AuthenticationMessage)message);

            else if (message instanceof GameSetUpMessage)
                notifyGameSetUp((GameSetUpMessage)message);

            else if (message instanceof CardMessage)
                notifyCard((CardMessage)message);

            else if (message instanceof SelectionMessage)
                notifySelection((SelectionMessage)message);

             else if (message instanceof ActionMessage)
                notifyAction((ActionMessage)message);

            else if (message instanceof DisconnectionMessage)
                notifyDisconnection();


        }
    }

    private void notifyDisconnection() {
        if (gameController != null)
            gameController.handleDisconnection(this);
        else
            server.handleDisconnection(this);
    }

    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        String nickname = authenticationMessage.nickname;
        server.tryAuthenticatingView(nickname, this);
    }
    public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage){
        gameController.createLobby(gameSetUpMessage,this);
    }
    public void notifyCard(CardMessage cardMessage){
        gameController.setPlayerCard(cardMessage, this);
    }
    public void notifySelection(SelectionMessage selectionMessage){
        gameController.calculateValidTiles(selectionMessage);
    }
    public void notifyAction(ActionMessage actionMessage){
        gameController.performAction(actionMessage, player);
    }
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage){
        //gameController.createLobby(gameSetUpMessage);
    }

    @Override
    public void updateGameCreation() {

    }


    public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {
        sendMessage(invalidNameMessage);
    }
    public void updateLobby(LobbyMessage lobbyMessage) {
        sendMessage(lobbyMessage);
    }
    public void updateWaitingList(WaitMessage waitMessage) {
        sendMessage(waitMessage);
    }
    public void updateValidTiles(ValidTilesMessage validTilesMessage) {
        sendMessage(validTilesMessage);
    }
    public void updateBoard(BoardMessage boardMessage) {
        sendMessage(boardMessage);
    }
    private void sendMessage(Object message){
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
