package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class ActionMessage {
    public Vector2d targetTile;
    public Vector2d currentTile;
    public boolean isPowerActive;

    ActionMessage(Vector2d targetTile, Vector2d currentTile, boolean isPowerActive){
        this.targetTile = targetTile;
        this.currentTile = currentTile;
        this.isPowerActive = isPowerActive;
    }

}