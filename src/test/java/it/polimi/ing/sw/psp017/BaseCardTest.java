package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.model.deck.BaseCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class BaseCardTest {
    @Test
    public void testIsValidMove() {
        Card card = new BaseCard();
        TestConfiguration testConfiguration = new TestConfiguration(card);

        Tile targetTile = testConfiguration.board.getTile(new Vector2d(0, 1));

        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);
        assertTrue("error:free tile", card.isValidMove(currentstep, null, testConfiguration.board));

        targetTile.setDome(true);
        assertFalse("error:occupied tile by dome", card.isValidMove(currentstep, null, testConfiguration.board));

        targetTile.setDome(false);
        targetTile.setWorker(testConfiguration.worker2);   //worker onnipresente
        assertFalse("error :occupied tile by worker", card.isValidMove(currentstep, null, testConfiguration.board));

        targetTile.setWorker(null);
        targetTile.setLevel(2);
        assertFalse("error: level target tile two step up", card.isValidMove(currentstep, null, testConfiguration.board));


    }

    @Test
    public void testIsValidBuilding() {
        Card card = new BaseCard();
        TestConfiguration testConfiguration = new TestConfiguration(card);

        Tile targetTile = testConfiguration.board.getTile(new Vector2d(0, 1));
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);
        assertTrue("error:free tile", card.isValidBuilding(currentstep, null, testConfiguration.board));

        targetTile.setDome(true);
        assertFalse("error:occupied tile by dome", card.isValidBuilding(currentstep, null, testConfiguration.board));

        targetTile.setDome(false);
        targetTile.setLevel(4);
        assertFalse("error:occupied tile by dome", card.isValidBuilding(currentstep, null, testConfiguration.board));

        targetTile.setDome(false);
        targetTile.setWorker(testConfiguration.worker2);
        assertFalse("error :occupied tile by worker", card.isValidBuilding(currentstep, null, testConfiguration.board));
        targetTile.setWorker(null);


    }

    @Test
    public void testCheckWin() {
        Card card = new BaseCard();
        TestConfiguration testConfiguration = new TestConfiguration(card);

        Tile targetTile = testConfiguration.game.getBoard().getTile(new Vector2d(0, 1));
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);
        targetTile.setLevel(3);
        assertTrue("error:free tile", card.checkWin(currentstep, null, testConfiguration.board));

        targetTile.setLevel(4);
        assertFalse("error:free tile", card.checkWin(currentstep, null, testConfiguration.board));

        targetTile.setLevel(2);
        assertFalse("error:free tile", card.checkWin(currentstep, null, testConfiguration.board));


    }

    @Test
    public void testMove() {
        Card card = new BaseCard();
        TestConfiguration testConfiguration = new TestConfiguration(card);

        Tile targetTile = testConfiguration.game.getBoard().getTile(new Vector2d(0, 1));
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);
        Worker tempworker = currentstep.getCurrentTile().getWorker();

        card.move(currentstep, null, testConfiguration.board);
        assertNull("error: worker not null in previous tile", currentstep.getCurrentTile().getWorker());
        assertSame(tempworker, currentstep.getTargetTile().getWorker());
        //la move toglie il worker dalla current tile e lo mette sulla target tile --> dopo la move il mio step è già aggiornato, non ho più il vecchio!

    }

    @Test
    public void testBuild() {
        Card card = new BaseCard();
        TestConfiguration testConfiguration = new TestConfiguration(card);

        Tile targetTile = testConfiguration.game.getBoard().getTile(new Vector2d(0, 1));
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);

        int oldlevel = currentstep.getCurrentTile().getLevel();
        card.build(currentstep, null, testConfiguration.board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        card.build(currentstep, null, testConfiguration.board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        card.build(currentstep, null, testConfiguration.board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());

        oldlevel = currentstep.getTargetTile().getLevel();
        card.build(currentstep, null, testConfiguration.board);
        assertEquals(oldlevel + 1, currentstep.getTargetTile().getLevel());
        assertTrue(currentstep.getTargetTile().isDome());


    }
}

