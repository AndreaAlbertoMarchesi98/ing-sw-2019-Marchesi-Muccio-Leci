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
    private ServerSocket socket;
    public final static int SOCKET_PORT = 7778;

    private final Queue<VirtualView> waitingViews;

    private final ArrayList<GameController> gameControllers;
    GameController waitingGameController;

    public Server() {
        waitingViews = new LinkedList<>();
        waitingGameController = null;
        gameControllers = new ArrayList<>();
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

        while (true)
            server.tryAddingViewToGame();

    }

    public void removeGameController(GameController gameController) {
        gameControllers.remove(gameController);
    }

    private VirtualView popWaitingView() {
        VirtualView view = waitingViews.poll();
        for (VirtualView waitingView : waitingViews)
            waitingView.updateWaitingRoom(new WaitMessage(waitingViews.size()));
        return view;
    }

    private synchronized void assignView(VirtualView view) {
        System.out.println("assigningView");
        waitingViews.add(view);
        if(!tryAddingViewToGame())
            view.updateWaitingRoom(new WaitMessage(waitingViews.size()));
    }

    private synchronized boolean tryAddingViewToGame() {
        //synchronized (waitingViews) {
            if (!waitingViews.isEmpty()) {
                if (waitingGameController == null) {
                    waitingGameController = new GameController(this, popWaitingView());
                    gameControllers.add(waitingGameController);
                    return true;
                } else if (waitingGameController.isLobbyJoinable()) {
                    waitingGameController.addPlayerToLobby(popWaitingView());
                    if(!waitingGameController.isLobbyJoinable())
                        waitingGameController = null;
                    return true;
                }
            }
            return false;
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
        for(GameController gameController : gameControllers) {
            for (VirtualView view : gameController.getViews()) {
                if (nickname.equals(view.getPlayer().getNickname()))
                    return false;
            }
        }
        return true;
    }

    public void handleDisconnection(VirtualView view) {
        waitingViews.remove(view);
    }

    public void run() {
        while (true) {
            try {
                Socket client = socket.accept();

                client.setSoTimeout(1000);
                System.out.println("new client connected");

                VirtualView virtualView = new VirtualView(client, this);
                Thread thread = new Thread(virtualView, "server_" + client.getInetAddress());
                thread.start();

            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }

}
