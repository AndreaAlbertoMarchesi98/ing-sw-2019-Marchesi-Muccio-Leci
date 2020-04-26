package it.polimi.ing.sw.psp017.controller.server;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.WaitMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class Server {

    private GameController gameController;
    private ServerSocket socket;
    public final static int SOCKET_PORT = 7777;

    private ArrayList<Player> playersInGame;
    private ArrayList<VirtualView> viewsInGame;
    private Queue<VirtualView> waitingViews;


    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }

        server.work();//to be fixed by creating 2 different threads,
        // one for accepting connections and one for creating games
    }

    private void work(){
        while (true) {
            acceptNewConnection();

            if (!waitingViews.isEmpty()) {
                if(!gameController.isGameRunning())
                    gameController.startGameCreation(popWaitingView());
                else if(!gameController.isLobbyFull())
                    gameController.addPlayerToLobby(popWaitingView());
            }
        }
    }

    private void acceptNewConnection() {
        try {
            Socket client = socket.accept();

            client.setSoTimeout(20);
            System.out.println("new client connected");

            VirtualView virtualView = new VirtualView(client, this);
            Thread thread = new Thread(virtualView, "server_" + client.getInetAddress());
            thread.start();


        } catch (IOException e) {
            System.out.println("connection dropped");
        }
    }


    private VirtualView popWaitingView(){
        VirtualView view = waitingViews.poll();
        for(VirtualView waitingView : waitingViews)
            waitingView.updateWaitingList(new WaitMessage(waitingViews.size()));
        return view;
    }
    private void addWaitingView(VirtualView view){
        view.updateWaitingList(new WaitMessage(waitingViews.size()));
        waitingViews.add(view);
    }

    public boolean tryAuthenticatingView(String nickname, VirtualView view) {
        if (isNicknameUnique(nickname)) {
            Player player = new Player(nickname);
            view.setPlayer(player);
            addWaitingView(view);
            return true;
        } else return false;
    }

    private boolean isNicknameUnique(String nickname) {
        for (VirtualView view : waitingViews) {
            if (nickname.equals(view.getPlayer().getNickname()))
                return false;
        }
        return true;
    }

    public void handleDisconnection(VirtualView view){

    }
}
