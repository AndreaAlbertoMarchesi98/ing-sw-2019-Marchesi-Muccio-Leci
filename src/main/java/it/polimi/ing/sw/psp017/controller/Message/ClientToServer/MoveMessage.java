package it.polimi.ing.sw.psp017.controller.Message.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class MoveMessage {
    public Vector2d targetTile;
    public Vector2d currentTile;
    public boolean isPowerActive;

    MoveMessage( Vector2d targetTile, Vector2d currentTile, boolean isPowerActive){
        this.targetTile = targetTile;
        this.currentTile = currentTile;
        this.isPowerActive = isPowerActive;
    }
}
