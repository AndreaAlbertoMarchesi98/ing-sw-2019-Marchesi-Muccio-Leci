package it.polimi.ing.sw.psp017.server.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
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

        player1 = new Player("PrometheusPlayer");
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
        currentStep.getTargetTile().setLevel(0);
        assertTrue("error: prometheus should  be able to move up", card.isValidMove(currentStep, previousStep, board));

        currentStep.setPowerActive(false);
        assertTrue("error: prometheus should  be able to move up", card.isValidMove(currentStep, previousStep, board));

        targetTile = board.getTile(new Vector2d(0, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        card.move(currentStep, previousStep, board);
        previousStep = currentStep;

        targetTile = board.getTile(new Vector2d(1, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertTrue("error: prometheus should be able to build", card.canBuild(0,true));
        card.build(currentStep, previousStep, board);

        assertFalse("Prometheus should NOT be able to choose",card.hasChoice(3));
        assertFalse("Prometheus should NOT be able to move",card.canMove(3,false));

        for(int i = 0; i < 4;i++)
        {
            if(i == 0) {
                assertTrue("Prometheus should be able to choose",card.hasChoice(i));
                assertFalse("error, should not be able to move",card.canMove(i,true));
            }

            else if(i==1)
            {
                assertTrue("error,should  be able to build",card.canBuild(i,false));
            }

            else assertFalse("error,should  be able to build",card.canBuild(i,false));




        }
    }
}