package it.polimi.ing.sw.psp017.controller;

import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.deck.*;
import it.polimi.ing.sw.psp017.view.GodName;

public  class CardFactory {


    public static Card getCard(GodName godName){

        if (godName == GodName.APOLLO)
            return new Apollo();
        else
            throw new NullPointerException();
    }
}
