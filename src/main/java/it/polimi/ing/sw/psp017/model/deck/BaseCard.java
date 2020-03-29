package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class BaseCard implements Card{


    @Override
    public boolean isValidMove(Tile currentTile, Tile targetTile) {
        if(targetTile.isDome() || targetTile.getWorker() != null){
            return false;
        }
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    @Override
    public boolean isValidBuilding(Tile targetTile) {
        return !targetTile.isDome() && targetTile.getWorker() == null;
    }

    @Override
    public boolean checkWin(Tile currentTile, Tile targetTile) {
        return targetTile.getLevel() == 3;
    }

    @Override
    public void move(Tile currentTile, Tile targetTile){
        Worker worker = currentTile.getWorker();
        worker.setPosition(targetTile.getPosition());
        targetTile.setWorker(worker);
        currentTile.setWorker(null);
    }

    @Override
    public void build(Tile targetTile) {
        targetTile.setLevel(targetTile.getLevel()+1);
    }
}
