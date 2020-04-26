package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class SelectionMessage {
    public Vector2d workerPosition;
    public boolean isPowerActive;

    public SelectionMessage(Vector2d workerTile, boolean isPowerActive) {
        this.workerTile = workerTile;
        this.isPowerActive = isPowerActive;
    }
}
