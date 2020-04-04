package it.polimi.ing.sw.psp017.controller;

import it.polimi.ing.sw.psp017.model.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public final static int SOCKET_PORT = 7777;

    private ArrayList<Player> playersInGame;

    private ArrayList<Player> waitingPlayers;

    private ServerState state;

    public ServerState getState() {
        return state;
    }

    public void setState(ServerState state) {
        this.state = state;
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }


        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client,server);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }


}
