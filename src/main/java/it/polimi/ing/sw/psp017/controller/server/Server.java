package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.InvalidNameMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.WaitMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Server implements Runnable {

    private GameController gameController;
    private ServerSocket socket;
    public final static int SOCKET_PORT = 7777;

    private ArrayList<Player> playersInGame;
    private ArrayList<VirtualView> viewsInGame;
    private Queue<VirtualView> waitingViews;

    public Server() {
        waitingViews = new LinkedList<>();
        gameController = new GameController();
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        Thread thread = new Thread(server);
        thread.start();

    }


    private void checkGameStatus() {
        /*
        if (!waitingViews.isEmpty()) {
            System.out.println("ain t empty");
            if (!gameController.isGameRunning())
                gameController.startGameCreation(popWaitingView());
            else if (!gameController.isLobbyFull())
                gameController.addPlayerToLobby(popWaitingView());
        }
        */
    }


    private VirtualView popWaitingView() {
        VirtualView view = waitingViews.poll();
        for (VirtualView waitingView : waitingViews)
            waitingView.updateWaitingRoom(new WaitMessage(waitingViews.size()));
        return view;
    }
    private void addWaitingView(VirtualView view) {
        view.updateWaitingRoom(new WaitMessage(waitingViews.size()));
        waitingViews.add(view);
    }

    private synchronized void assignView(VirtualView view){
        System.out.println("assigningView");
        if(waitingViews.isEmpty()) {
            if (gameController.isGameCreatable())
                gameController.startGameCreation(view);
            else if (gameController.isLobbyJoinable())
                gameController.addPlayerToLobby(view);
            else
                addWaitingView(view);
        }
        else
            addWaitingView(view);
    }

    public synchronized void tryAuthenticatingView(String nickname, VirtualView view) {
        if (isNicknameUnique(nickname)) {
            Player player = new Player(nickname);
            view.setPlayer(player);
            assignView(view);
        } else {
            view.updateLoginScreen(new InvalidNameMessage());
        }
    }

    private boolean isNicknameUnique(String nickname) {
        for (VirtualView view : waitingViews) {
            if (nickname.equals(view.getPlayer().getNickname()))
                return false;
        }
        for (VirtualView view : gameController.getViews()) {
            if (nickname.equals(view.getPlayer().getNickname()))
                return false;
        }
        return true;
    }

    public void handleDisconnection(VirtualView view) {

    }

    public void run() {
        while (true)
            try {
                Socket client = socket.accept();

                //client.setSoTimeout(20);
                System.out.println("new client connected");

                VirtualView virtualView = new VirtualView(client, this);
                Thread thread = new Thread(virtualView, "server_" + client.getInetAddress());
                thread.start();


            } catch (IOException e) {
                System.out.println("connection dropped");
            }
    }

}
