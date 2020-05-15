package it.polimi.ing.sw.psp017.model;

/**
 * each player has 2 workers
 */
public class Worker {
    private Player owner;
    private Tile tile;

    public Worker(Player owner) {
        this.owner = owner;
        owner.addWorker(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Player getOwner() {
        return owner;
    }
}
