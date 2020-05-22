package it.polimi.ing.sw.psp017.model.deck;


import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class HephaestusTest {
    private Board board;
    private GameTest game;
    private Player player1;
    private Worker worker1P1;


    @Before
    public void init() {
        game = new GameTest();

        player1 = new Player("HephaestusPlayer");
        game.addPlayer(player1);
        board = game.getBoard();
        player1.setCard(new Hephaestus());

        worker1P1 = new Worker(player1);


        board.addWorker(worker1P1, new Vector2d(0, 0));
    }

    @Test
    public void hephaestusDoubleBuildTest() {
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

        assertTrue("error: hephaestus should be able to build", card.canBuild(2,true));

        targetTile = board.getTile(new Vector2d(0, 0));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertTrue("error: hephaestus should be able to build here", card.isValidBuilding(currentStep, previousStep, board));

        targetTile = board.getTile(new Vector2d(0, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertFalse("error: hephaestus should not be able to build on another place", card.isValidBuilding(currentStep, previousStep, board));
    }

    @Test
    public void hephaestusDomeTest() {
        Card card = player1.getCard();

        board.getTile(new Vector2d(1, 1)).setLevel(2);

        Tile targetTile = board.getTile(new Vector2d(1, 1));
        Step currentStep = new Step(worker1P1.getTile(), targetTile, false);
        Step previousStep = null;
        card.build(currentStep, previousStep, board);
        previousStep = currentStep;

        targetTile = board.getTile(new Vector2d(1, 1));
        currentStep = new Step(worker1P1.getTile(), targetTile, true);
        assertFalse("error: hephaestus should not be able to build a dome", card.isValidBuilding(currentStep, previousStep, board));
    }
}