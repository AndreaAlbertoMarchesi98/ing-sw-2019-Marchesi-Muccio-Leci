package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.view.GodName;

import java.io.Serializable;
import java.util.ArrayList;

public class GameSetUpMessage implements Serializable {
    public ArrayList<GodName> godNames;

    public GameSetUpMessage(ArrayList<GodName> godNames) {
        this.godNames = godNames;
    }
}
