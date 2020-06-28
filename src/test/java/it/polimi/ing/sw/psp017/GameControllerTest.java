package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.controller.GameController;
import it.polimi.ing.sw.psp017.server.Server;
import it.polimi.ing.sw.psp017.server.VirtualView;
import it.polimi.ing.sw.psp017.server.model.*;
import it.polimi.ing.sw.psp017.client.view.GodName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameControllerTest {
    private static final int pingInterval = 1000;
    private final static int UNDO_WAIT_TIME = 100;
    private final static int SERVER_PORT = 7778;
    private final static String ip = "127.0.0.1";
    private static boolean setupDone = false;
    private static Server server;
    private static GameController gameController;
    private static VirtualView view1;
    private static VirtualView view2;
    private static VirtualView view3;
    private static class UndoThread implements Runnable{
        private final VirtualView view;
        private static final int undoDelay = 100;

        public UndoThread(VirtualView view) {
            this.view = view;
        }
        public void run()
        {
            try {
                Thread.sleep(undoDelay);
                view.notifyUndo(new UndoMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class PingSender implements Runnable{
        private final ObjectOutputStream output;
        public PingSender(Socket socket) throws IOException {
            output = new ObjectOutputStream(socket.getOutputStream());
        }
        public void run()
        {
                try {
                    output.writeObject(new ClientPing());
                    Thread.sleep(pingInterval);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
    @Before
    public void init() throws IOException {
        if(!setupDone){
            server=new Server(SERVER_PORT);
            setupDone=true;

            Socket socket1 = new Socket(ip, SERVER_PORT);
            Socket socket2 = new Socket(ip, SERVER_PORT);
            Socket socket3 = new Socket(ip, SERVER_PORT);

            new Thread(new PingSender(socket1)).start();
            new Thread(new PingSender(socket2)).start();
            new Thread(new PingSender(socket3)).start();

            view1 = new VirtualView(socket1, server);
            view2 = new VirtualView(socket2, server);
            view3 = new VirtualView(socket3, server);
        }
        view1.setPlayer(new Player("player1"));
        view2.setPlayer(new Player("player2"));
        view3.setPlayer(new Player("player3"));
        gameController = new GameController(server, view1, UNDO_WAIT_TIME);
    }
    @AfterClass
    public static void closeServer() throws IOException {
        server.getServerSocket().close();
        server.stop();
    }
    @Test
    public void normal3PlayersGameTest(){
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATLAS);
        chosenCards.add(GodName.ATHENA);
        chosenCards.add(GodName.HEPHAESTUS);
        assertFalse("error: lobby should not be joinable", gameController.isLobbyJoinable());

        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController.addViewToLobby(view2);
        assertTrue("error: lobby should be joinable", gameController.isLobbyJoinable());

        gameController.addViewToLobby(view3);
        assertFalse("error: lobby should not be joinable", gameController.isLobbyJoinable());

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
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));
        //ATHENA EFFECT ON OTHERS
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 1)));
        //ATLAS SKIPS POWER
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 2)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 2)));
    }
    @Test
    public void decoratorTest(){
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.ATHENA);
        chosenCards.add(GodName.ATLAS);
        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController.addViewToLobby(view2);
        view2.notifyCard(new CardMessage(GodName.ATHENA));
        view1.notifyCard(new CardMessage(GodName.ATLAS));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        assertNull("error: there should be no worker ", gameController.getGame().getBoard().getTile(new Vector2d(3, 1)).getWorker());
    }
    @Test
    public void disconnectionTest(){
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.DEMETER);
        chosenCards.add(GodName.PROMETHEUS);
        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController.addViewToLobby(view2);
        view2.notifyCard(new CardMessage(GodName.DEMETER));
        view1.notifyCard(new CardMessage(GodName.PROMETHEUS));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        //PLAYER_1 DISCONNECTS
        gameController.handleDisconnection(view1);
        assertFalse("error: the view has been removed", gameController.getViews().contains(view1));
    }
    @Test
    public void victoryTest(){
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
        Tile winningTile = gameController.getGame().getBoard().getTile(new Vector2d(2,0));
        winningTile.setLevel(3);
        Tile standingTile = gameController.getGame().getBoard().getTile(new Vector2d(1,0));
        standingTile.setLevel(2);
        //PLAYER_1 MOVE
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        assertFalse("",server.getGameControllers().contains(gameController));
    }
    @Test
    public void wrongSelectionTest(){
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
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(5, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        //PLAYER_2 MOVE
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(-20, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 3)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
    }
    @Test
    public void undoTest(){
        GameController gameController1 = new GameController(server,view1,400);
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.PAN);
        chosenCards.add(GodName.ATHENA);
        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController1.addViewToLobby(view2);
        view2.notifyCard(new CardMessage(GodName.PAN));
        view1.notifyCard(new CardMessage(GodName.ATHENA));
        //WORKERS PLACEMENT
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        //PLAYER1 MOVES AND UNDOES
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        new Thread(new UndoThread(view1)).start();
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));
        assertNotNull("error: undo has not been executed", gameController1.getGame().getBoard().getTile(new Vector2d(1, 0)).getWorker());

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 0)));

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        new Thread(new UndoThread(view2)).start();
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        assertNull("error: undo has not been executed", gameController1.getGame().getBoard().getTile(new Vector2d(2, 1)).getWorker());
    }
    @Test
    public void instantDeathTest(){
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.PAN);
        chosenCards.add(GodName.ARTEMIS);
        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController.addViewToLobby(view2);
        view2.notifyCard(new CardMessage(GodName.PAN));
        view1.notifyCard(new CardMessage(GodName.ARTEMIS));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));

        gameController.getGame().getBoard().getTile(new Vector2d(0,1)).setLevel(2);
        gameController.getGame().getBoard().getTile(new Vector2d(1,1)).setLevel(2);

        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 1)));
        assertNull("error: the game should be over", gameController.getGame().getBoard().getTile(new Vector2d(3, 1)).getWorker());
    }
    @Test
    public void noMovesSelectionTest(){
        ArrayList<GodName> chosenCards = new ArrayList<>();
        chosenCards.add(GodName.PAN);
        chosenCards.add(GodName.ATHENA);
        view1.notifyGameSetUp(new GameSetUpMessage(chosenCards));
        gameController.addViewToLobby(view2);
        view2.notifyCard(new CardMessage(GodName.PAN));
        view1.notifyCard(new CardMessage(GodName.ATHENA));

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));

        gameController.getGame().getBoard().getTile(new Vector2d(0,1)).setLevel(3);

        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 0)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(4, 4)));
        view1.notifySelectedTile(new SelectedTileMessage(new Vector2d(3, 3)));
        assertNotNull("error: could not change selected worker", gameController.getGame().getBoard().getTile(new Vector2d(3, 3)).getWorker());
    }

    @Test
    public void playerDeathTest(){
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
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 0)));
        view2.notifySelectedTile(new SelectedTileMessage(new Vector2d(2, 1)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(0, 1)));
        view3.notifySelectedTile(new SelectedTileMessage(new Vector2d(1, 1)));
        assertNull("error: the player didn't die", gameController.getGame().getBoard().getTile(new Vector2d(0, 0)).getWorker());
    }
}
