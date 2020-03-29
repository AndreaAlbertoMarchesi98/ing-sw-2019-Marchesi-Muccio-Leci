package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class Apollo extends BaseCard {

    boolean isEnemyWorker(Worker worker, Worker targetWorker) {
        if(targetWorker!=null)
            return targetWorker.getOwner() != worker.getOwner();
        else
            return false;
    }

    @Override
    public boolean isValidMove(Tile currentTile, Tile targetTile) {
        Worker targetWorker = targetTile.getWorker();
        Worker worker=currentTile.getWorker();
        if (targetTile.isDome() || !isEnemyWorker(worker, targetWorker))
            return false;
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    @Override
    public void move(Tile currentTile, Tile targetTile) {
        if (targetTile.getWorker() == null)
            super.move(currentTile, targetTile);
        else
            swapWorkers(currentTile,targetTile);
    }

    void swapWorkers(Tile currentTile, Tile targetTile){
        Worker worker = currentTile.getWorker();
        Worker enemyWorker = targetTile.getWorker();

        targetTile.setWorker(worker);
        currentTile.setWorker(enemyWorker);

        worker.setPosition(targetTile.getPosition());
        enemyWorker.setPosition(currentTile.getPosition());
    }
}
