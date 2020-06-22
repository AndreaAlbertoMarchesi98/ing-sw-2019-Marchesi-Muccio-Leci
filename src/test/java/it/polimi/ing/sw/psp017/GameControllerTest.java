package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.controller.GameController;
import it.polimi.ing.sw.psp017.server.Server;
import it.polimi.ing.sw.psp017.server.VirtualView;
import it.polimi.ing.sw.psp017.server.model.*;
import it.polimi.ing.sw.psp017.client.view.GodName;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
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
    private Thread t1;
    private Thread t2;
    private Thread t3;
    private Thread t4;

    private static class UndoThread implements Runnable{
        private final VirtualView view;

        public UndoThread(VirtualView view) {
            this.view = view;
        }

        public void run()
        {
            view.notifyUndo(new UndoMessage());
        }
    }

    //before class setta roba
    @Before
    public void init() throws IOException {
        server = new Server(SERVER_PORT);

        serverSocket = new   ServerSocket(SERVER_PORT);


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

        gameController = new GameController(server, view1,5000);
    }

    @Test
    public void normal3PlayersGameTest() throws IOException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATLAS);
        chosenCards.add(GodName.ATHENA);
        chosenCards.add(GodName.HEPHAESTUS);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);
        gameController.addViewToLobby(view3);

        view3.notifyCard(new CardMessage(GodName.ATLAS));
        view2.notifyCard(new CardMessage(GodName.ATHENA));
        view1.notifyCard(new CardMessage(GodName.HEPHAESTUS));

        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));

        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 2)));

        //HEPHAESTUS SKIPS POWER
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        view1.notifyIsPowerActive(new PowerActiveMessage(false));

        //ATHENA NO EFFECT ON OTHERS
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));

        //ATLAS USES POWER
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));
        view3.notifyIsPowerActive(new PowerActiveMessage(true));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 2)));

        //HEPHAESTUS USES POWER
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(5, 0)));

        //ATHENA EFFECT ON OTHERS
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));

        //ATLAS SKIPS POWER
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));

        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }

    @Test
    public void disconnectionTest() throws IOException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATLAS);
        chosenCards.add(GodName.ARTEMIS);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);

        view2.notifyCard(new CardMessage(GodName.ARTEMIS));
        view1.notifyCard(new CardMessage(GodName.ATLAS));

        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));

        //PLAYER_1 DISCONNECTS
        gameController.handleDisconnection(view1);

        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }

    @Test
    public void victoryTest() throws IOException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.MINOTAUR);
        chosenCards.add(GodName.APOLLO);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);

        view2.notifyCard(new CardMessage(GodName.MINOTAUR));
        view1.notifyCard(new CardMessage(GodName.APOLLO));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        //PLAYER_2 MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        //PLAYER_2 MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));
        //PLAYER_2 MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));
        //PLAYER_2 WINNING MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));

        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }

    @Test
    public void wrongSelectionTest() throws IOException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.MINOTAUR);
        chosenCards.add(GodName.APOLLO);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);

        view2.notifyCard(new CardMessage(GodName.MINOTAUR));
        view1.notifyCard(new CardMessage(GodName.APOLLO));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        //PLAYER_2 MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 3)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));

        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }

    @Test
    public void UndoTest() throws IOException, InterruptedException {

        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.PAN);
        chosenCards.add(GodName.ATHENA);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);

        view2.notifyCard(new CardMessage(GodName.PAN));
        view1.notifyCard(new CardMessage(GodName.ATHENA));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        //PLAYER1 MOVES AND UNDOES
        new Thread(new UndoThread(view1));
        view1.notifyUndo(new UndoMessage());
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        new Thread(new UndoThread(view1));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        new Thread(new UndoThread(view1));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));

        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }

    @Test
    public void SynchronizationMadnessTest() throws IOException, InterruptedException {
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.PAN);
        chosenCards.add(GodName.ATHENA);

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));

        gameController.addViewToLobby(view2);

        view2.notifyCard(new CardMessage(GodName.PAN));
        view1.notifyCard(new CardMessage(GodName.ATHENA));


        //assertTrue("error:checkWin false but level tile is 3",);
        serverSocket.close();
    }
}
