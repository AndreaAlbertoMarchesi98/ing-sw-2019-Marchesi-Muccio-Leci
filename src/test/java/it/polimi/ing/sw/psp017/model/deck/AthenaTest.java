package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AthenaTest {
    private Board board;
    private GameTest game;
    private Player player1;
    private Player player2;
    private Worker worker1P1;
    private Worker worker1P2;


    @Before
    public void init() {
        game = new GameTest();

        player1 = new Player("AthenaPlayer");
        player2 = new Player("PanPlayer");
        game.addPlayer(player1);
        game.addPlayer(player2);
        board = game.getBoard();
        player1.setCard(new Athena());
        player2.setCard(new Pan());

        worker1P1 = new Worker(player1);
        worker1P2 = new Worker(player2);


        board.addWorker(worker1P1, new Vector2d(0, 0));
        board.addWorker(worker1P2, new Vector2d(0, 1));
    }

    @Test
    public void athenaEffectOnTest() {
        Card cardP1 = player1.getCard();

        board.getTile(new Vector2d(2, 0)).setLevel(1);
        board.getTile(new Vector2d(1, 1)).setLevel(1);

        Tile targetTile1 = board.getTile(new Vector2d(1, 0));
        Tile targetTile2 = board.getTile(new Vector2d(2, 0));

        Step previousStep = new Step(worker1P1.getTile(), targetTile1, false);
        Step currentStep = new Step(targetTile1, targetTile2, false);

        if (cardP1.hasActiveDecorator(currentStep, previousStep, board))
            player2.setCard(cardP1.getDecorator(player2.getCard()));

        Tile targetTile = board.getTile(new Vector2d(1, 1));
        currentStep = new Step(worker1P2.getTile(), targetTile, false);


        assertFalse("error: opponent should not be able to move up", player2.getCard().isValidMove(currentStep, null, board));
    }

    @Test
    public void athenaEffectOffTest() {
        Card cardP1 = player1.getCard();
        Card cardP2 = player1.getCard();

        board.getTile(new Vector2d(1, 0)).setLevel(1);

        Tile targetTile1 = board.getTile(new Vector2d(1, 0));
        Tile targetTile2 = board.getTile(new Vector2d(2, 0));

        Step previousStep = new Step(worker1P1.getTile(), targetTile1, false);
        Step currentStep = new Step(targetTile1, targetTile2, false);

        if (cardP1.hasActiveDecorator(currentStep, previousStep, board))
            player2.setCard(cardP1.getDecorator(player2.getCard()));

        Tile targetTile = board.getTile(new Vector2d(1, 0));
        currentStep = new Step(worker1P2.getTile(), targetTile, false);


        assertTrue("error: opponent should be able to move up", cardP2.isValidMove(currentStep, null, board));
    }
}
