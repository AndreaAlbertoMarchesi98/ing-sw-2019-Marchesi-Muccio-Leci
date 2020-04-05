package it.polimi.ing.sw.psp017.model;

public class Worker {
    private Player owner;
    private Vector2d position;

    public Worker(Player owner){
        this.owner = owner;
        //this.position = Vector2d;
    }

    public Tile getCurrentTile(Board board) throws NullPointerException{
        return board.getTile(position);
    }
    public Tile getTargetTile(Board board, Vector2d direction) throws NullPointerException{
        return board.getTile(Vector2d.sumVectors(position,direction));
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    public void setPosition(Vector2d workerPosition) {
        this.position = workerPosition;
    }
    public Player getOwner() {
        return owner;
    }
}
