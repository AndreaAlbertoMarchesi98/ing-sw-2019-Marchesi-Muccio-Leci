package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.server.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class CardDecoratorTEst {
    private Board board;
    private GameTest game;
    private Player myPlayer;
    private Player player2;
    private Player player1;
    private Worker worker1P1;
    private Worker worker2P1;
    private Worker worker1P2;
    private Worker worker2P2;


    @Before
    public void init(){

        game = new GameTest();

        myPlayer = new Player("decorator");
        player2 = new Player("Player2");
        game.addPlayer(myPlayer);
        game.addPlayer(player2);
        board = game.getBoard();




        worker1P1 = new Worker(myPlayer);
        worker2P1 = new Worker(myPlayer);
        worker1P2 = new Worker(player2);
        worker2P2 = new Worker(player2);

        board.addWorker(worker1P1, new Vector2d(0,0));
        board.addWorker(worker2P1,new Vector2d(1,0));

    }

    @Test
    public void testCardDecorator() {

        Card cardDecorator = new CardDecorator(new BaseCard()) {
            @Override
            public boolean hasChoice(int stepNumber) {
                return super.hasChoice(stepNumber);
            }

            @Override
            public boolean canMove(int stepNumber, boolean isPowerActive) {
                return super.canMove(stepNumber, isPowerActive);
            }

            @Override
            public boolean canBuild(int stepNumber, boolean isPowerActive) {
                return super.canBuild(stepNumber, isPowerActive);
            }

            @Override
            public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
                return super.isValidMove(currentStep, previousStep, board);
            }

            @Override
            public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
                return super.isValidBuilding(currentStep, previousStep, board);
            }

            @Override
            public boolean checkWin(Step currentStep, Board board) {
                return super.checkWin(currentStep, board);
            }

            @Override
            public void move(Step currentStep, Step previousStep, Board board) {
                super.move(currentStep, previousStep, board);
            }

            @Override
            public void build(Step currentStep, Step previousStep, Board board) {
                super.build(currentStep, previousStep, board);
            }

            @Override
            public boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board) {
                return super.hasActiveDecorator(currentStep, previousStep, board);
            }

            @Override
            public Card getDecorator(Card cardToDecorate) {
                return super.getDecorator(cardToDecorate);
            }
        };

        myPlayer.setCard(cardDecorator);
        player2.setCard(cardDecorator);
        for(int i = 0;i < 4; i++)
        {
            if (i == 0) assertTrue("error, should be moving",cardDecorator.canMove(i,false));
            if (i == 1) assertTrue("error, should be building",cardDecorator.canBuild(i,false));
            assertFalse("error, should have no choice",cardDecorator.hasChoice(i));

        }





        Tile targetTile = board.getTile(new Vector2d(0, 1));

        Step currentstep = new Step(worker1P1.getTile(), targetTile, false);


        assertTrue("error: isValidMove false but tile free ", myPlayer.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(true);
        assertFalse("error:isValidMove true but tile occupied  by dome", myPlayer.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(false);
        targetTile.setWorker(worker1P2);
        assertFalse("error :isValidMove true but tile occupied  by worker", myPlayer.getCard().isValidMove(currentstep, null, board));

        targetTile.setWorker(null);
        targetTile.setLevel(2);
        assertFalse("error: isValidMove true but level target tile is two step up", myPlayer.getCard().isValidMove(currentstep, null, board));

        Card card = myPlayer.getCard();

        assertFalse("error",card.hasActiveDecorator(currentstep,null,board));



        card.getDecorator(card);


    }

}
