package it.polimi.ing.sw.psp017.server.model;

/**
 * this class is used to remember the previous choice of each player
 * in order to be used with the various divinity cards
 */
public class Step {
    volatile Tile currentTile;
    volatile Tile targetTile;
    volatile boolean powerActive;

    public Step(Tile currentTile, Tile targetTile, boolean powerActive) {
        this.currentTile = currentTile;
        this.targetTile = targetTile;
        this.powerActive = powerActive;
    }

    public Tile getCurrentTile() {
        return currentTile;
    }

    public void setCurrentTile(Tile currentTile) {
        this.currentTile = currentTile;
    }

    public Tile getTargetTile() {
        return targetTile;
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
    }

    public boolean isPowerActive() {
        return powerActive;
    }

    public void setPowerActive(boolean powerActive) {
        this.powerActive = powerActive;
    }
}
