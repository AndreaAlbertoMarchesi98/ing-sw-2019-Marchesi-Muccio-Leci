package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BaseCardTest {
    private Board board;
    private GameTest game;
    private Player player1;
    private Player player2;
    private Worker worker1P1;
    private Worker worker2P1;
    private Worker worker1P2;
    private Worker worker2P2;

    @Before
    public void init(){

        game = new GameTest();

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

    }

    @Test
    public void testCheckWin() {


        Tile targetTile = board.getTile(new Vector2d(0, 1));
        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);
        targetTile.setLevel(3);
        assertTrue("error:checkWin false but level tile is 3",player1.getCard().checkWin(currentstep,  board));


        targetTile.setLevel(2);
        assertFalse("error: checkWin true but free tile with build level 2", player1.getCard().checkWin(currentstep,  board));

        //il nostro checkwin non controlla se tutti gli altri worker sono bloccati
        //il server deve vedere quando gli altri son bloccati e dirgli che hanno perso
        //notifyGameover quando non hai più mosse

    }

    @Test
    public void testMove() {


        Tile targetTile = board.getTile(new Vector2d(0, 1));
        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);

        player1.getCard().move(currentstep, null, board);
        assertNull("error: worker not null in previous tile", currentstep.getCurrentTile().getWorker());
        assertSame("error: worker null in targetTile",worker1P1, currentstep.getTargetTile().getWorker());

    }

    @Test
    public void testBuild() {



        Tile targetTile = board.getTile(new Vector2d(0, 1));
        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);

        int oldlevel = currentstep.getCurrentTile().getLevel();
        player1.getCard().build(currentstep, null, board);
        assertEquals("error: no adding level after build",oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        player1.getCard().build(currentstep, null, board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        player1.getCard().build(currentstep, null, board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        player1.getCard().build(currentstep, null, board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());
        assertTrue("error: no set dome at level 4", currentstep.getTargetTile().isDome());


    }

}

