package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

public class Prometheus extends BaseCard {

    @Override
    public boolean hasChoice(int stepNumber){
        return stepNumber == 0;
    }

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

    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if(currentStep.isPowerActive())
            return super.isValidMove(currentStep, previousStep, board);
        else{
            if(targetTile.getLevel()>currentTile.getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }
    }
}
