package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.client.NetworkHandler;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.server.GameController;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.controller.server.VirtualView;
import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.model.Vector2d;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.View;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private final static int SERVER_PORT = 7778;
    private final static String ip = "127.0.0.1";
    private Server server;
    private View dummyView;
    private Client dummyClient;
    private NetworkHandler networkHandler1;
    private NetworkHandler networkHandler2;
    private NetworkHandler networkHandler3;
    private NetworkHandler networkHandler4;
    private NetworkHandler networkHandler5;
    private NetworkHandler networkHandler6;
    private NetworkHandler networkHandler7;
    private NetworkHandler networkHandler8;
    private NetworkHandler networkHandler9;
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
        server = new Server(SERVER_PORT);
       // dummyView = new CLI(dummyClient);
        networkHandler1 = new NetworkHandler();
        networkHandler2 = new NetworkHandler();
        networkHandler3 = new NetworkHandler();
        networkHandler4 = new NetworkHandler();
        networkHandler5 = new NetworkHandler();
        networkHandler6 = new NetworkHandler();
    }

    @Test
    public void tonsOfConnectionsTest() throws IOException {
        networkHandler1.startConnection();
        networkHandler2.startConnection();
        networkHandler3.startConnection();
        networkHandler4.startConnection();

        networkHandler1.sendMessage(new AuthenticationMessage("name1"));
        networkHandler2.sendMessage(new AuthenticationMessage("name2"));
        networkHandler3.sendMessage(new AuthenticationMessage("name3"));
        networkHandler4.sendMessage(new AuthenticationMessage("name4"));

        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATLAS);
        chosenCards.add(GodName.ATHENA);
        chosenCards.add(GodName.HEPHAESTUS);
        networkHandler1.sendMessage(new GameSetUpMessage(chosenCards));

        networkHandler5.startConnection();
        networkHandler6.startConnection();
        networkHandler7.startConnection();

        networkHandler5.sendMessage(new AuthenticationMessage("name1"));
        networkHandler6.sendMessage(new AuthenticationMessage("name2"));

        networkHandler8.startConnection();

        networkHandler7.sendMessage(new AuthenticationMessage("name3"));
        networkHandler8.sendMessage(new AuthenticationMessage("name4"));
    }

    @Test
    public void sameNicknamesTest() throws IOException {
        networkHandler1.startConnection();
        networkHandler2.startConnection();
        networkHandler3.startConnection();
        networkHandler4.startConnection();

        networkHandler1.sendMessage(new AuthenticationMessage("sameName"));
        networkHandler2.sendMessage(new AuthenticationMessage("name2"));
        networkHandler3.sendMessage(new AuthenticationMessage("sameName"));
        networkHandler4.sendMessage(new AuthenticationMessage("sameName"));

        assertEquals("error: there should be only 2 players", 2, server.getWaitingViews().size());
    }
}
