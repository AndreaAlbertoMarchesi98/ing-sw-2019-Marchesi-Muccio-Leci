package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArtemisTest {


    private Board board;
    private GameTest game;
    private Player player1;
    private Player enemyPlayer;
    private Worker worker1P1;
    private Worker worker1Enemy;



    @Before
    public void init(){
        game = new GameTest();

        player1 = new Player("DemeterPlayer");
        game.addPlayer(player1);
        board = game.getBoard();
        player1.setCard(new Artemis());

        worker1P1 = new Worker(player1);
        worker1Enemy = new Worker(enemyPlayer);


        board.addWorker(worker1P1, new Vector2d(0,0));
        board.addWorker(worker1Enemy,new Vector2d(1,1));
    }

    /**
     * stepNumber 0 = move, 1=build
     */
    @Test
    public void artemisDoubleBuildTest() {
        Card card = player1.getCard();

        assertTrue("error: artemis should be able to move", card.canMove(1,true));

        assertTrue("error: artemis cannot move", card.canMove(0,false));
        assertFalse("error: artemis cannot move", card.canMove(1,false));
        assertFalse("error: artemis cannot build", card.canBuild(1,true));
        assertTrue("error: artemis cannot build", card.canBuild(1,false));
        assertFalse("error: artemis cannot move", card.canMove(2,true));
        assertTrue("error: artemis should be able to build", card.canBuild(2,true));
        assertFalse("error: artemis cannot build", card.canBuild(4,true));

        //artemis dovrebbe poter costruire nello step 3 sia con power che non

        Tile targetTile = board.getTile(new Vector2d(0, 1));
        Step currentStep = new Step(worker1P1.getTile(), targetTile, false);
        Step previousStep = null;

        assertTrue("error: demeter should be able to move",card.isValidMove(currentStep,previousStep,board));
        card.move(currentStep, previousStep, board);    //new position [0,1]
        previousStep = currentStep; //[0,0]
        currentStep.setPowerActive(true);
        assertFalse("error",card.isValidMove(currentStep,previousStep,board));
        previousStep=null;
        assertFalse("error",card.isValidMove(currentStep,previousStep,board));

        assertFalse("error: demeter cannot move to it's previous position",card.isValidMove(currentStep,previousStep,board));

        for(int i = 0; i < 3;i++)
        {
            if(i == 1) assertTrue("Artemis should be able to choose",card.hasChoice(i));
            else{
                assertFalse("Artemis should NOT be able to choose",card.hasChoice(i));
            }

        }






    }


}