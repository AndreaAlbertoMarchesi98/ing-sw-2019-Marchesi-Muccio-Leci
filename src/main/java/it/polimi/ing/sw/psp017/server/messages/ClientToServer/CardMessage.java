package it.polimi.ing.sw.psp017.server.messages.ClientToServer;

import it.polimi.ing.sw.psp017.client.view.GodName;

import java.io.Serializable;



public class CardMessage implements Serializable {
    public GodName godName;

    public CardMessage(GodName godName) {

        this.godName = godName;
    }
}
