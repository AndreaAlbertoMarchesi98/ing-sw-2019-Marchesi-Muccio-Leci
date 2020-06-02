package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Step;

/**
 * Hephaestus's card
 * God of Blacksmiths
 * Your Build: Your Worker may
 * build one additional block (not dome)
 * on top of your first block.
 */
public class Hephaestus extends BaseCard {

    /**
     * Hephaestus's power allow worker to build twice in the same but this happen only
     * in the third step
     *
     * @param stepNumber is the step of the current turn
     * @return true if this is the third step of the turn
     */
    @Override
    public boolean hasChoice(int stepNumber) {
        return stepNumber == 2;
    }

    /**
     *in addition to the default construction logic, construction is also allowed to build if
     * Hephaestus's power is activated
     * @param stepNumber current step of the turn
     * @param isPowerActive if true it change default game's logic
     * @return true if worker can build
     */
    @Override
    public boolean canBuild(int stepNumber, boolean isPowerActive) {
        switch (stepNumber) {
            case 0:
            case 1:
                return true;
            case 2:
                return isPowerActive;
            default:
                return false;
        }
    }


    /**
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return true worker is allowed to build considering Hephaestus' power
     */
    @Override
    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        if (currentStep.isPowerActive()){
            if (currentStep.getTargetTile().getLevel() < 3)
                return currentStep.getTargetTile() == previousStep.getTargetTile();
            else
                return false;
        }
        else
            return super.isValidBuilding(currentStep, previousStep, board);
    }
}
