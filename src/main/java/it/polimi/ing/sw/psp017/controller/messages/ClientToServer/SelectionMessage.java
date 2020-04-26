package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class SelectionMessage {
    public Vector2d workerPosition;
    public boolean isPowerActive;

    public SelectionMessage(Vector2d workerPosition, boolean isPowerActive) {
        this.workerPosition = workerPosition;
        this.isPowerActive = isPowerActive;
    }
}
