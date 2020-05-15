package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

/**
 * Demeter's card
 * Goddess of the Harvest
 * Your Build: Your Worker may
 * build one additional time, but not
 * on the same space.
 */
public class Demeter extends BaseCard {

    /**
     * Demeter's power allow player to build one additional time in the third step
     *
     * @param stepNumber is the step of the current turn
     * @return true if it is the third step
     */
    public boolean hasChoice(int stepNumber) {
        return stepNumber == 2;
    }

    /**
     * Demeter's logic build
     *
     * @param stepNumber    current step of the turn
     * @param isPowerActive if true it change default game's logic
     * @return true if the worker can build in this stepNumber turn
     */
    public boolean canBuild(int stepNumber, boolean isPowerActive) {
        switch (stepNumber) {
            case 1:
                return true;
            case 2:
                return isPowerActive;
            default:
                return false;
        }

    }


    /**
     * this method adds a further possibility to build in addition to the default one of the base card:
     * if Demeter's power is activated the worker is allowed to build an additional time but not in the
     * same tile.
     *
     * @param currentStep  contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board        main board used to manage the game
     * @return true if there is no dome and no worker on the target tile and also if the worker is building in a
     * diffent tile
     */
    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        if (currentStep.isPowerActive()) {
            if (!previousStep.getTargetTile().equals(currentStep.getTargetTile())) {
                return super.isValidBuilding(currentStep, previousStep, board);
            } else
                return false;
        } else
            return super.isValidBuilding(currentStep, previousStep, board);
    }

}
