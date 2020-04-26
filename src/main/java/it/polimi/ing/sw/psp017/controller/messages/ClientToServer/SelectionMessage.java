package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

import java.io.Serializable;

public class SelectionMessage implements Serializable {
    public Vector2d workerPosition;
    public boolean isPowerActive;

    public SelectionMessage(Vector2d workerPosition, boolean isPowerActive) {
        this.workerPosition = workerPosition;
        this.isPowerActive = isPowerActive;
    }
}
