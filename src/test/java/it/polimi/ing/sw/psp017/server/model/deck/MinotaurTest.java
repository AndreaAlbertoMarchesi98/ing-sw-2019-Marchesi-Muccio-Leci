package it.polimi.ing.sw.psp017.server.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MinotaurTest {
    private Board board;
    private GameTest game;
    private Player MinotaurPlayer;
    private Player enemyPlayer;
    private Worker minotaurWorker;
    private Worker enemyWorker1;
    private Worker enemyWorker2;


    @Before
    public void init(){
        game = new GameTest();

        MinotaurPlayer = new Player("Minotaur");
        enemyPlayer = new Player("enemy");
        game.addPlayer(MinotaurPlayer);
        game.addPlayer(enemyPlayer);
        board = game.getBoard();
        MinotaurPlayer.setCard(new Minotaur());
        enemyPlayer.setCard(new BaseCard());

        minotaurWorker = new Worker(MinotaurPlayer);
        enemyWorker1 = new Worker(MinotaurPlayer);
        enemyWorker2 = new Worker(enemyPlayer);


        board.addWorker(minotaurWorker, new Vector2d(0,1));
        board.addWorker(enemyWorker1,new Vector2d(0,0));
        board.addWorker(enemyWorker2,new Vector2d(2,2));

    }

    @Test
    public void minotaurIsValidMoveTest() {


        Card card = MinotaurPlayer.getCard();
        Tile targetTile = enemyWorker1.getTile();

        Step currentStep = new Step(minotaurWorker.getTile(),targetTile,false);
        assertFalse("error, Minotaur cannot move on enemy worker",card.isValidMove(currentStep,null,board));

        currentStep.setPowerActive(true);
        assertFalse("error, Minotaur cannot move on enemy worker",card.isValidMove(currentStep,null,board));

        currentStep.setTargetTile(new Tile(new Vector2d(0,1)));
        assertTrue("minotaur should able to move",card.isValidMove(currentStep,null,board));

        targetTile=enemyWorker2.getTile();

        currentStep= new Step(minotaurWorker.getTile(),targetTile,true);
        assertTrue("minotaur should able to move",card.isValidMove(currentStep,null,board));
        currentStep.setPowerActive(true);
        assertTrue("minotaur should not be able to move",card.isValidMove(currentStep,null,board));


        Tile previousEnemyTile =enemyWorker2.getTile();
        card.move(currentStep,null,board);
        assertEquals(previousEnemyTile,minotaurWorker.getTile());

        currentStep = new Step(minotaurWorker.getTile(),new Tile(new Vector2d(3,2)),false);
        card.move(currentStep,null,board);



    }



}