package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.View;
import java.util.concurrent.locks.Lock;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Queue;

public class VirtualView implements Runnable, View {

    private Player player;
    private Server server;
    private GameController gameController;
    private ObjectInputStream input;
    private ObjectOutputStream output;

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
                notifyNickname((AuthenticationMessage)message);

            else if (message instanceof GameSetUpMessage)
                notifyGameSetUp((GameSetUpMessage)message);

            else if (message instanceof CardMessage)
                notifyCard((CardMessage)message);

            else if (message instanceof PlacementMessage)
                notifyPlacement((PlacementMessage)message);

            else if (message instanceof SelectionMessage)
                notifySelection((SelectionMessage)message);

            else if (message instanceof PowerActiveMessage)
                notifyIsPowerActive((PowerActiveMessage) message);

             else if (message instanceof ActionMessage)
                notifyAction((ActionMessage)message);

            else if (message instanceof DisconnectionMessage)
                notifyDisconnection();


        }
    }

    private void notifyDisconnection() {
        System.out.println("inside disxconnection");
        //synchronized (server.getWaitingViews()) {
            if (gameController != null)
                gameController.handleDisconnection(this);
            else
                server.handleDisconnection(this);
        //}
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

    @Override
    public void notifyPlacement(PlacementMessage placementMessage) {
        gameController.placeWorkers(placementMessage);
    }

    @Override
    public void notifySelection(SelectionMessage selectionMessage) {
        gameController.selectWorker(selectionMessage);
    }

    public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage){
        gameController.setPowerActive(powerActiveMessage);
    }
    public void notifyAction(ActionMessage actionMessage){
        gameController.performAction(actionMessage, player);
    }
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage){
        //gameController.createLobby(gameSetUpMessage);
    }

    public void updateGameCreation() {
        System.out.println(getPlayer().getNickname()+": update game creation");
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
    public void updateDisconnection(SDisconnectionMessage disconnectionMessage) {
        sendMessage(disconnectionMessage);
    }

    private void sendMessage(Object message){
        try {
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
