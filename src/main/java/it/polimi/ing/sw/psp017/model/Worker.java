package it.polimi.ing.sw.psp017.model;

/**
 * each player has 2 workers
 */
public class Worker {
    private Player owner;
    private Vector2d position;

    public Worker(Player owner) {
        this.owner = owner;
        owner.addWorker(this);
        //this.position = Vector2d;
    }

    public Tile getTile(Board board) throws NullPointerException {
        return board.getTile(position);
    }


    public void setPosition(Vector2d workerPosition) {
        this.position = workerPosition;
    }

    public Player getOwner() {
        return owner;
    }
}
