package it.polimi.ing.sw.psp017.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PrometheusTest  {
    private Board board;
    private GameTest game;
    private Player player1;
    private Worker worker1P1;


    @Before
    public void init(){
        game = new GameTest();

        player1 = new Player("PlayerPrometheus");
        game.addPlayer(player1);
        board = game.getBoard();
        player1.setCard(new Prometheus());

        worker1P1 = new Worker(player1);


        board.addWorker(worker1P1, new Vector2d(0,0));
    }

    @Test
    public void prometheusDoubleMoveTest() {
        Card card = player1.getCard();

        assertTrue("error: prometheus should be able to build", card.canBuild(0,true));

        Tile targetTile = board.getTile(new Vector2d(1, 1));
        Step currentStep = new Step(worker1P1.getTile(), targetTile, true);
        Step previousStep = null;
        card.build(currentStep, previousStep, board);
        previousStep = currentStep;

        assertTrue("error: prometheus should be able to move", card.canMove(1,true));

        targetTile = board.getTile(new Vector2d(1, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertFalse("error: prometheus should not be able to move up", card.isValidMove(currentStep, previousStep, board));

        targetTile = board.getTile(new Vector2d(0, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        card.move(currentStep, previousStep, board);
        previousStep = currentStep;

        targetTile = board.getTile(new Vector2d(1, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertTrue("error: prometheus should be able to build", card.canBuild(0,true));
        card.build(currentStep, previousStep, board);
    }
}