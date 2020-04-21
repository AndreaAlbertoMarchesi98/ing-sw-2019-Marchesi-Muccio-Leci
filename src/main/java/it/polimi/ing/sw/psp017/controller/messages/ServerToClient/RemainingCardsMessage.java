package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class RemainingCardsMessage {
    public ArrayList<GodName> cards;

    public RemainingCardsMessage(ArrayList<GodName> cards){
        this.cards = cards;
    }
}
