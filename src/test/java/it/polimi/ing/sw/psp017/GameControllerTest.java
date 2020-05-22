package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.GameSetUpMessage;
import it.polimi.ing.sw.psp017.controller.server.GameController;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.controller.server.VirtualView;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameControllerTest {
    private Server server;
    private GameController gameController;
    private VirtualView view1;
    private VirtualView view2;
    private VirtualView view3;
/*
    @Before
    public void init() throws IOException {

        view1 = new VirtualView(new Socket(), server);
        view2 = new VirtualView(new Socket(), server);

        view1.setPlayer(new Player("player1"));
        view2.setPlayer(new Player("player2"));

        gameController = new GameController(server, view1);

        ArrayList<GodName> cards = new ArrayList<>();
        cards.add(GodName.APOLLO);
        gameController.createLobby(new GameSetUpMessage({new Arra;

        player1 = new Player("Player1");
        player2 = new Player("Player2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        board = game.getBoard();
        Card card = new BaseCard();
        player1.setCard(card);
        player2.setCard(card);

        worker1P1 = new Worker(player1);
        worker2P1 = new Worker(player1);
        worker1P2 = new Worker(player2);
        worker2P2 = new Worker(player2);

        board.addWorker(worker1P1, new Vector2d(0,0));
        board.addWorker(worker2P1,new Vector2d(1,0));

    }

    @Test
    public void testIsValidMove() {


        Tile targetTile = board.getTile(new Vector2d(0, 1));

        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);
        assertTrue("error: isValidMove false but tile free ", player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(true);
        assertFalse("error:isValidMove true but tile occupied  by dome",player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(false);
        targetTile.setWorker(worker1P2);
        assertFalse("error :isValidMove true but tile occupied  by worker",player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setWorker(null);
        targetTile.setLevel(2);
        assertFalse("error: isValidMove true but level target tile is two step up", player1.getCard().isValidMove(currentstep, null, board));
    }

    @Test
    public void testIsValidBuilding() {

        Tile targetTile = board.getTile(new Vector2d(0, 1));

        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);
        assertTrue("error: isValidBuilding false but tile free ", player1.getCard().isValidBuilding(currentstep, null, board));

        targetTile.setDome(true);
        assertFalse("error:isValidBuilding true but tile occupied by dome",player1.getCard().isValidBuilding(currentstep, null, board));

        targetTile.setDome(false);
        targetTile.setLevel(4);
        assertFalse("error:isValidBuilding true but tile occupied  by dome", player1.getCard().isValidBuilding(currentstep, null, board));

        targetTile.setDome(false);
        targetTile.setWorker(worker1P2);
        assertFalse("error :isValidBuilding true but tile occupied by an enemy worker",player1.getCard().isValidBuilding(currentstep, null, board));
        targetTile.setWorker(null);
        board.addWorker(worker1P1, targetTile.getPosition());
        assertFalse("error:isValidBuilding true but tile occupied by a worker of mine", player1.getCard().isValidBuilding(currentstep, null, board));

    }*/
}
