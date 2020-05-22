package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.server.GameController;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.controller.server.VirtualView;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerTest {
    private final static int SERVER_PORT = 7778;
    private final static String ip = "127.0.0.1";
    private Socket client1;
    private Socket client2;
    private Socket client3;
    private Server server;
    private ServerSocket serverSocket;
    private GameController gameController;
    private VirtualView view1;
    private VirtualView view2;
    private VirtualView view3;

    //before class setta roba
    @Before
    public void init() throws IOException {

        serverSocket = new ServerSocket(SERVER_PORT);


        Socket socket1 = new Socket(ip, SERVER_PORT);
        client1 = serverSocket.accept();
        Socket socket2 = new Socket(ip, SERVER_PORT);
        client2 = serverSocket.accept();
        Socket socket3 = new Socket(ip, SERVER_PORT);
        client3 = serverSocket.accept();

        ObjectOutputStream output1 = new ObjectOutputStream(socket1.getOutputStream());
        output1.writeObject(new ClientPing());
        ObjectOutputStream output2 = new ObjectOutputStream(socket2.getOutputStream());
        output2.writeObject(new ClientPing());
        ObjectOutputStream output3 = new ObjectOutputStream(socket3.getOutputStream());
        output3.writeObject(new ClientPing());

        view1 = new VirtualView(client1, server);
        view2 = new VirtualView(client2, server);
        view3 = new VirtualView(client3, server);

        view1.setPlayer(new Player("player1"));
        view2.setPlayer(new Player("player2"));
        view3.setPlayer(new Player("player3"));

        gameController = new GameController(server, view1);



    }

    @Test
    public void normal3PlayersGameTest() throws InterruptedException, IOException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATLAS);
        chosenCards.add(GodName.ATHENA);
        chosenCards.add(GodName.DEMETER);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);
        gameController.addViewToLobby(view3);


        view3.notifyCard(new CardMessage(GodName.ATLAS));
        view2.notifyCard(new CardMessage(GodName.ATHENA));
        view1.notifyCard(new CardMessage(GodName.DEMETER));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0,0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1,0)));

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0,1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1,1)));

        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(0,2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1,2)));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1,0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2,0)));
        view1.notifyIsPowerActive(new PowerActiveMessage(true));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2,0)));

        assertTrue("error:checkWin false but level tile is 3",true);

        serverSocket.close();
    }
}
