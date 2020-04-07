package it.polimi.ing.sw.psp017.model.deck;
import it.polimi.ing.sw.psp017.CommonTest;
import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import org.junit.Before;
import org.junit.Test;

public class ArtemisTest {
    Card card;

    @Before
    public void setCard() {
        card = CardFactory.getCard(GodName.ARTEMIS);

    }


    @Test
    public void artemisTest() {
        CommonTest.commonTest(GodName.ARTEMIS);

    }



}