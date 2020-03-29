package it.polimi.ing.sw.psp017.model;

public class Worker {
    private Player owner;
    private Vector2d position;

    public Worker(Player owner, Vector2d Vector2d){
        this.owner = owner;
        this.position = Vector2d;
    }

    public Tile getCurrentTile() throws NullPointerException{
        return Game.Board.getTiles()[getPosition().x][getPosition().y];
    }
    public Tile getTargetTile(Vector2d direction) throws NullPointerException{
        Vector2d targetPosition=Vector2d.sumVectors(getPosition(),direction);
        return Game.Board.getTiles()[targetPosition.x][targetPosition.y];
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

    public Vector2d getPosition() {
        return position;
    }
}
