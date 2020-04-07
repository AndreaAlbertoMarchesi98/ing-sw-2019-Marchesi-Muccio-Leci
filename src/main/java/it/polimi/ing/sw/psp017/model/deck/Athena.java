package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

import it.polimi.ing.sw.psp017.model.decorators.*;
import it.polimi.ing.sw.psp017.view.GodName;

/**
 * Athena's card
 *
 * Goddess of the Hunt
 * Your Move: Your Worker may
 * move one additional time, but not
 * back to its initial space.
 */
public class Athena extends BaseCard{
    private CardDecorator athenaDecorator;

    public Athena(){
        name=GodName.ATHENA;
    }

    public CardDecorator getCardDecorator() {
        return athenaDecorator;
    }

    @Override
    public boolean hasDecorator(){
        return true;
    }


}
