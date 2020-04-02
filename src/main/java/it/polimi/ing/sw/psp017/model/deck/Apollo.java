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
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        Worker targetWorker = targetTile.getWorker();
        Worker worker=currentTile.getWorker();
        if (targetTile.isDome() || !isEnemyWorker(worker, targetWorker))
            return false;
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    @Override
    public void move(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if (targetTile.getWorker() == null)
            super.move(currentStep, previousStep, board);
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
