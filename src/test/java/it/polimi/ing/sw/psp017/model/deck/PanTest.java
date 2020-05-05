package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.CommonTest;
import it.polimi.ing.sw.psp017.TestConfiguration;
import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PanTest  {
    Card card;

    @Before
    public void setCard() {
        card = CardFactory.getCard(GodName.PAN);

    }
    @Test
    public void panTest(){
        TestConfiguration testConfiguration = new TestConfiguration(card);

        testConfiguration.currentTile.setLevel(2);
        Tile targetTile = testConfiguration.board.getTile(new Vector2d(1,0));
         Step currentStep = new Step(testConfiguration.currentTile, targetTile, false);
        assertTrue(card.checkWin(currentStep,testConfiguration.board));
    }


}