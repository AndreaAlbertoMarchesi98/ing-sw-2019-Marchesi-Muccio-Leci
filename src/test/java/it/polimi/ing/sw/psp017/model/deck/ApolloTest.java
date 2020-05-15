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
        /*
        TestConfiguration testConfiguration = new TestConfiguration(card);
        Tile targetTile = testConfiguration.worker2.getCurrentTile(testConfiguration.board);
        Step currentstep = new Step(testConfiguration.currentTile, targetTile, false);

        if (card.isValidMove(currentstep, null, testConfiguration.board)) {
            Tile apollotempworkerTile = testConfiguration.worker1.getCurrentTile(testConfiguration.board);
            Tile enemytempworkertile = testConfiguration.worker2.getCurrentTile(testConfiguration.board);

            card.move(currentstep, null, testConfiguration.board);
            assertSame(apollotempworkerTile, testConfiguration.worker2.getCurrentTile(testConfiguration.board));
            assertSame(enemytempworkertile, testConfiguration.worker1.getCurrentTile(testConfiguration.board));
        }

        assertTrue("not valid move", card.isValidMove(currentstep, null, testConfiguration.board));
        */

    }
}

