package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;
import java.io.Serializable;

public class ActionMessage implements Serializable {
    public Vector2d targetTile;
    //current tile
    //ispoweractive

    public ActionMessage(Vector2d targetTile){
        this.targetTile = targetTile;

    }

}