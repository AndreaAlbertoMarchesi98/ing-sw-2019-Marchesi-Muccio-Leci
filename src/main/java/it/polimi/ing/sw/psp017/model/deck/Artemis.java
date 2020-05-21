package it.polimi.ing.sw.psp017.model.deck;


import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

/**
 * this is Artemis' card
 * Goddess of the Hunt
 * Your Move: Your Worker may
 * move one additional time, but not
 * back to its initial space.
 */
public class Artemis extends BaseCard {

    /**
     * Artemis' power is valid only in the second step of the turn which allows her to move a second time
     *
     * @param stepNumber step of the current turn
     * @return true if artemis' power is activated in that specif stepNumber
     */
    @Override
    public boolean hasChoice(int stepNumber) {
        return stepNumber == 1;
    }

    /**
     * Artemis' logic move
     *
     * @param stepNumber    step of the current turn
     * @param isPowerActive boolean
     * @return a boolean which tell if the workers is allowed to move
     */
    @Override
    public boolean canMove(int stepNumber, boolean isPowerActive) {
        switch (stepNumber) {
            case 0:
                return true;
            case 1:
                return isPowerActive;
            default:
                return false;
        }
    }

    /**
     * Artemis' logic build
     *
     * @param step          step of the current turn
     * @param isPowerActive boolean
     * @return a boolean which tell if the workers is allowed to move
     */
    @Override
    public boolean canBuild(int step, boolean isPowerActive) {
        switch (step) {
            case 1:
                return !isPowerActive;
            case 2:
                return isPowerActive;
            default:
                return false;
        }
    }

    /**
     * this method check if the target tile selected is granted standard by logic game except when the player select
     * to activate Artemis' power in the second step. if isPowerActivated is true , the workers can move one more
     * time but not in the previous position.
     *
     * @param currentStep  contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board        main board used to manage the game
     * @return true if move is allowed
     */
    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        if (currentStep.isPowerActive()) {
            if (previousStep != null && targetTile != previousStep.getCurrentTile())
                return super.isValidMove(currentStep, previousStep, board);
            else
                return false;
        } else
            return super.isValidMove(currentStep, previousStep, board);
    }
}