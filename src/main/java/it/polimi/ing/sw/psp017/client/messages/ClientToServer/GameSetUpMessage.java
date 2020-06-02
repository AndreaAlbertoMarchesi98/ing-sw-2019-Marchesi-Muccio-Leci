package it.polimi.ing.sw.psp017.client.messages.ClientToServer;

import it.polimi.ing.sw.psp017.client.view.GodName;

import java.io.Serializable;
import java.util.ArrayList;


//notify client -> serve
//first player select 3 cards from the deck
public class GameSetUpMessage implements Serializable {

    public ArrayList<GodName> godNames;

    public GameSetUpMessage(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }
}
