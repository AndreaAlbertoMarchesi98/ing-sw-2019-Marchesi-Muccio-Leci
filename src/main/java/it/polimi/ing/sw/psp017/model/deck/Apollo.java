package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;
import it.polimi.ing.sw.psp017.model.Worker;

/**
 *this is Apollo's card
 * God Of Music
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space by forcing their Worker to
 * the space yours just vacated.
 */
public class Apollo extends BaseCard {

    /**
     *check if the worker selected is an enemy worker
     * @param worker current player's worker
     * @param targetWorker tile of the enemy worker
     * @return boolean; true if the targetWorker is actually an enemy , otherwise false
     */
    boolean isEnemyWorker(Worker worker, Worker targetWorker) {
        if(targetWorker!=null)
            return targetWorker.getOwner() != worker.getOwner();
        else
            return false;
    }

    /**
     * check if the currentStep selected is allowed
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return boolean; return false if the selected tile is a dome or occupied by an enemy
     *          or if the high difference between target tile and current tile is < 2; otherwise return true
     */
    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        Worker targetWorker = targetTile.getWorker();
        Worker worker=currentTile.getWorker();
        if(!isEnemyWorker(worker, targetWorker)){
            return super.isValidMove(currentStep,previousStep,board);
        }
        else return targetTile.getLevel() - currentTile.getLevel() < 2;

    }

    /**
     *if possible goes to Apollo's swap power otherwise proceed with the standard move
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board  main board used to manage the game
     */
    @Override
    public void move(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if (targetTile.getWorker() == null)
            super.move(currentStep, previousStep, board);
        else
            swapWorkers(currentTile,targetTile);
    }

    /**
     *Apollo's swap power
     *swap player's worker with neighboring enemy or does the standard mov
     * @param currentTile contains information about in which tile the worker is and what move wants to do next
     * @param targetTile contains information about last step
     */
    void swapWorkers(Tile currentTile, Tile targetTile){
        Worker worker = currentTile.getWorker();
        Worker enemyWorker = targetTile.getWorker();

        targetTile.setWorker(worker);
        currentTile.setWorker(enemyWorker);
    }
}
