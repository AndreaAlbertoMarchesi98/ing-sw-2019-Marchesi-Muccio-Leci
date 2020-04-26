package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

import java.io.Serializable;

public class PlacementMessage implements Serializable {
    public Vector2d placement;

    public PlacementMessage(Vector2d placement) {
        this.placement = placement;
    }
}
