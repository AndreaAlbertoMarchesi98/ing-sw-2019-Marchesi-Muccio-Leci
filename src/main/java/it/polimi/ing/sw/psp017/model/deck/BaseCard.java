package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class BaseCard implements Card{

    public boolean hasDecorator(){
        return false;
    }

    public boolean canMove(int step) {
        return step == 0;
    }

    public boolean canBuild(int step) {
        return step == 1;
    }

    public boolean isValidMove(Tile currentTile, Tile targetTile) {
        if(targetTile.isDome() || targetTile.getWorker() != null){
            return false;
        }
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    public boolean isValidBuilding(Tile targetTile) {
        return !targetTile.isDome() && targetTile.getWorker() == null;
    }

    public boolean checkWin(Tile currentTile, Tile targetTile) {
        return targetTile.getLevel() == 3;
    }

    public void move(Tile currentTile, Tile targetTile){
        Worker worker = currentTile.getWorker();
        worker.setPosition(targetTile.getPosition());
        targetTile.setWorker(worker);
        currentTile.setWorker(null);
    }

    public void build(Tile targetTile) {
        targetTile.setLevel(targetTile.getLevel()+1);
    }

}
