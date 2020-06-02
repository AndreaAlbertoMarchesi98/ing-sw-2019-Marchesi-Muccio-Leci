package it.polimi.ing.sw.psp017.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtlasTest {
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

        player1 = new Player("PlayerAtlas");
        player2 = new Player("Player2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        board = game.getBoard();
        player1.setCard(new Atlas());
        player2.setCard(new BaseCard());
        board.getTile(new Vector2d(1,3)).setLevel(1);
        board.getTile(new Vector2d(2,3)).setLevel(2);
        board.getTile(new Vector2d(3,3)).setLevel(3);
        board.getTile(new Vector2d(3,4)).setLevel(1);  //dome

        //Atlas
        worker1P1 = new Worker(player1);
        worker2P1 = new Worker(player1);

        //BaseCard
        worker1P2 = new Worker(player2);
        worker2P2 = new Worker(player2);


        //atlas
        board.addWorker(worker1P1, new Vector2d(1,2));
        board.addWorker(worker2P1,new Vector2d(2,4));


        //baseCard
        board.addWorker(worker1P2, new Vector2d(1,1));
        board.addWorker(worker2P2,new Vector2d(2,1));


    }


    @Test
    public void atlasBuildTest() {

        Card card = player1.getCard(); //atlas
        Tile targetTile = worker1P2.getTile();
        Step currentstep = new Step(worker1P1.getTile(), targetTile, true);

        //cannot build dome on occupied tile
        assertFalse("error: valid build false. Target tile occupied by a enemy worker", card.isValidBuilding(currentstep, null, board));


        //build dome on your feet
        targetTile = worker2P1.getTile();
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertFalse("error: cannot build a dome on your feet", card.isValidBuilding(currentstep, null, board));


        //build on building at level 0
        targetTile = board.getTile(new Vector2d(0,3));
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertTrue("error: is allowed to build a dome at level 0", card.isValidBuilding(currentstep, null, board));


        //build on building at level 1
        targetTile = board.getTile(new Vector2d(2,3));
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertTrue("error: is allowed to build a dome at level 1", card.isValidBuilding(currentstep, null, board));


        //build on building at level 2
        targetTile = board.getTile(new Vector2d(2,3));
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertTrue("error: is allowed to build a dome at level 2", card.isValidBuilding(currentstep, null, board));

        //build on building at level 3
        targetTile = board.getTile(new Vector2d(3,3));
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertTrue("error: is allowed to build a dome at level 3", card.isValidBuilding(currentstep, null, board));


        //build on dome
        targetTile = board.getTile(new Vector2d(3,4));
        currentstep = new Step(worker2P1.getTile(), targetTile, true);
        assertTrue("error: cannot build on a dome with a dome", card.isValidBuilding(currentstep, null, board));


    }


}