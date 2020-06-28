package it.polimi.ing.sw.psp017.server.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DemeterTest  {
    private Board board;
    private GameTest game;
    private Player player1;
    private Worker worker1P1;


    @Before
    public void init(){
        game = new GameTest();

        player1 = new Player("DemeterPlayer");
        game.addPlayer(player1);
        board = game.getBoard();
        player1.setCard(new Demeter());

        worker1P1 = new Worker(player1);


        board.addWorker(worker1P1, new Vector2d(0,0));
    }

    @Test
    public void demeterDoubleBuildTest() {
        Card card = player1.getCard();

        Tile targetTile = board.getTile(new Vector2d(1, 1));
        Step currentStep = new Step(worker1P1.getTile(), targetTile, false);
        Step previousStep = null;
        card.move(currentStep, previousStep, board);
        previousStep = currentStep;

        targetTile = board.getTile(new Vector2d(0, 0));
        currentStep = new Step(worker1P1.getTile(), targetTile, false);
        card.build(currentStep, previousStep, board);
        previousStep = currentStep;

        assertTrue("error: demeter should be able to build", card.canBuild(2,true));

        targetTile = board.getTile(new Vector2d(0, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertTrue("error: demeter should be able to build here", card.isValidBuilding(currentStep, previousStep, board));

        targetTile = board.getTile(new Vector2d(0, 0));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertFalse("error: demeter should not be able to build on the same place", card.isValidBuilding(currentStep, previousStep, board));

        currentStep.setPowerActive(false);
        assertTrue("error: demeter should  be able to build on the same place", card.isValidBuilding(currentStep, previousStep, board));

        for(int i =0;i <4;i++)
        {
            if(i==1) assertTrue("demeter should build",card.canBuild(i,false));
             else assertFalse("demeter should not be able to build",card.canBuild(i,false));
             if(i==2) assertTrue("error, DEmeter has a choice",card.hasChoice(i));
             else assertFalse("error, DEmeter has no choice",card.hasChoice(i));
        }
    }
}