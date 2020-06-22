package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.client.NetworkHandler;
import it.polimi.ing.sw.psp017.server.controller.GameController;
import it.polimi.ing.sw.psp017.server.Server;
import it.polimi.ing.sw.psp017.server.VirtualView;
import it.polimi.ing.sw.psp017.client.view.CLI;
import it.polimi.ing.sw.psp017.client.view.View;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {/*
    private final static int SERVER_PORT = 7778;
    private final static String ip = "127.0.0.1";
    private Server server;
    private View dummyView;
    private Client dummyClient;
    private ArrayList<NetworkHandler> networkHandlers;
    private Socket client1;
    private Socket client2;
    private Socket client3;
    private ServerSocket serverSocket;
    private GameController gameController;
    private VirtualView view1;
    private VirtualView view2;
    private VirtualView view3;
    private Thread t1;
    private Thread t2;
    private Thread t3;
    private Thread t4;

    @Before
    public void init() throws IOException {
        networkHandlers = new ArrayList<>();
        server = new Server(SERVER_PORT);
        dummyView = new CLI(dummyClient);
        for (int i = 0; i < 10; i++) {
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.setView(dummyView);
            networkHandlers.add(networkHandler);
        }

    }

    @Test
    public void tonsOfConnectionsTest() throws IOException {
        /**
         * networkHandlers.get(0).startConnection();
         *         networkHandlers.get(1).startConnection();
         *         networkHandlers.get(2).startConnection();
         *         networkHandlers.get(3).startConnection();
         *
         *         networkHandlers.get(0).sendMessage(new AuthenticationMessage("name1"));
         *         networkHandlers.get(1).sendMessage(new AuthenticationMessage("name2"));
         *         networkHandlers.get(2).sendMessage(new AuthenticationMessage("name3"));
         *         networkHandlers.get(3).sendMessage(new AuthenticationMessage("name4"));
         *
         *         ArrayList<GodName> chosenCards = new ArrayList<>();
         *         chosenCards.add(GodName.ATLAS);
         *         chosenCards.add(GodName.ATHENA);
         *         chosenCards.add(GodName.HEPHAESTUS);
         *         networkHandlers.get(0).sendMessage(new GameSetUpMessage(chosenCards));
         *
         *         networkHandlers.get(5).startConnection();
         *         networkHandlers.get(6).startConnection();
         *         networkHandlers.get(7).startConnection();
         *
         *         networkHandlers.get(5).sendMessage(new AuthenticationMessage("name1"));
         *         networkHandlers.get(6).sendMessage(new AuthenticationMessage("name2"));
         *
         *         networkHandlers.get(8).startConnection();
         *
         *         networkHandlers.get(7).sendMessage(new AuthenticationMessage("name3"));
         *         networkHandlers.get(8).sendMessage(new AuthenticationMessage("name4"));
         */
    //}
/*
    @Test
    public void sameNicknamesTest() throws IOException {

        assertEquals("error: there should be only 2 players", 1);
    }*/
}
