package it.polimi.ing.sw.psp017.client.messages.ClientToServer;

import it.polimi.ing.sw.psp017.server.model.Vector2d;

import java.io.Serializable;

public class SelectedTileMessage implements Serializable {
    public Vector2d tilePosition;
    //current tile
    //ispoweractive

    public SelectedTileMessage(Vector2d tilePosition){
        this.tilePosition = tilePosition;

    }

}