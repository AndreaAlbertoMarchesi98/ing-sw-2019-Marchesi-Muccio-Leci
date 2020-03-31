package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Card;

public class Athena extends BaseCard{

    private boolean movedUp=true;
    public boolean hasMovedUp(){
        return movedUp;
    }
    @Override
    public boolean hasDecorator(){
        return true;
    }

}
