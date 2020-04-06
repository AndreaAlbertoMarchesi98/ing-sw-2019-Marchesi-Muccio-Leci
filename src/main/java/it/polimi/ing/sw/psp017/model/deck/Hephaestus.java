package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

public class Hephaestus extends BaseCard {
    @Override
    public boolean hasChoice(int stepNumber) {
        return stepNumber == 2;
    }

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


    @Override
    public void build(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        targetTile.setLevel(targetTile.getLevel() + 1);
        // if(targetTile.getLevel()<3) currentStep.setPowerActive(true);
    }

    @Override
    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        if (!currentStep.isPowerActive()) return super.isValidBuilding(currentStep, previousStep, board);
        else {
            if (currentStep.getTargetTile().getLevel() < 3)
                return currentStep.getTargetTile() == previousStep.getTargetTile();
            else return false;
        }

    }
}
