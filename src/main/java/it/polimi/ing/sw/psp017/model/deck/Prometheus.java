package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

/**
 * Prometheus' card
 * Titan Benefactor of Mankind
 * Your Turn: If your Worker does
 * not move up, it may build both
 * before and after moving.
 */
public class Prometheus extends BaseCard {

    /**
     * Prometheus owner has two different moves on the first step
     *
     * @param stepNumber is the step of the current turn
     * @return true if it is first step
     */
    @Override
    public boolean hasChoice(int stepNumber){
        return stepNumber == 0;
    }

    /**
     *
     *
     * @param stepNumber current step of the turn
     * @param isPowerActive if true it change default game's logic
     * @return true if worker can move also considering Prometheus power
     */
    @Override
    public boolean canMove(int stepNumber, boolean isPowerActive) {
        switch (stepNumber) {
            case 0:
                return !isPowerActive;
            case 1:
                return isPowerActive;
            default:
                return false;
        }
    }

    /**
     *
     *
     * @param step step of the current turn
     * @param isPowerActive if true it change default game's logic
     * @return true if worker can build also considering Prometheus power
     */
    @Override
    public boolean canBuild(int step, boolean isPowerActive) {
        switch (step) {
            case 0:
                return isPowerActive;
            case 1:
            case 2:
                return !isPowerActive;
            default:
                return false;
        }
    }

    /**
     * if player has choose to us Prometheus power , player's workers can't move up this turn
     * else default moves logic
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return true if move il allowed
     */
    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if(currentStep.isPowerActive()){
            if(targetTile.getLevel()>currentTile.getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }
        else
            return super.isValidMove(currentStep, previousStep, board);
    }
}
