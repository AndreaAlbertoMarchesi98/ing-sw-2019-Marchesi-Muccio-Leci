package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

import java.io.Serializable;


//user select a tile in the board
public class SelectionMessage implements Serializable {
    public Vector2d workerPosition;
    public boolean isPowerActive;//da cancellare!!!!!!!!!!!!!!!!


    public SelectionMessage(Vector2d workerPosition, boolean isPowerActive) {
        this.workerPosition = workerPosition;
        this.isPowerActive = isPowerActive;//da cancellare!!!!!!!!!!!!!!!!!1
    }
}
