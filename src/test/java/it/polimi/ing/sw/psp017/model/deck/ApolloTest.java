package it.polimi.ing.sw.psp017.model.deck;
import it.polimi.ing.sw.psp017.*;
import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class ApolloTest {
    Card card;

    @Before
    public void setCard() {
        card = CardFactory.getCard(GodName.APOLLO);

    }

    @Test
    public void apolloTestSuite() {
        CommonTest.commonTest(GodName.APOLLO);
    }

    @Test
    public void apolloMoveTest() {
        TestConfiguration testConfiguration = new TestConfiguration(card);
        Tile targetTile = testConfiguration.worker2.getCurrentTile(testConfiguration.game.getBoard());
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);
        if (card.isValidMove(currentstep, null, testConfiguration.game.getBoard())) {
            Tile apollotempworkerTile = testConfiguration.worker1.getCurrentTile(testConfiguration.game.getBoard());
            Tile enemytempworkertile = testConfiguration.worker2.getCurrentTile(testConfiguration.game.getBoard());
            card.move(currentstep, null, testConfiguration.game.getBoard());
            assertSame(apollotempworkerTile, testConfiguration.worker2.getCurrentTile(testConfiguration.game.getBoard()));
            assertSame(enemytempworkertile, testConfiguration.worker1.getCurrentTile(testConfiguration.game.getBoard()));
        }
        assertTrue("not valid move", card.isValidMove(currentstep, null, testConfiguration.game.getBoard()));
    }
}

