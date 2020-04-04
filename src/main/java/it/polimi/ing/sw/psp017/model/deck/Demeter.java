package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

public class Demeter extends BaseCard {

    public boolean hasChoice(int stepNumber) {
        return stepNumber == 2;
    }

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


    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        if (currentStep.isPowerActive()) {
            if (previousStep.getTargetTile().equals(currentStep.getTargetTile())) {
                return super.isValidBuilding(currentStep, previousStep, board);
            } else return false;
        } else
            return super.isValidBuilding(currentStep, previousStep, board);
    }

}
