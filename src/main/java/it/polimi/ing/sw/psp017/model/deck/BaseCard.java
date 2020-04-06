package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;

public class BaseCard implements Card {

    protected GodName name;

    public GodName getName() {
        return name;
    }

    public boolean hasDecorator() {
        return false;
    }

    public boolean hasChoice(int stepNumber) {
        return false;
    }

    public boolean canMove(int stepNumber, boolean isPowerActive) {
        return stepNumber == 0;
    }

    public boolean canBuild(int stepNumber, boolean isPowerActive) {
        return stepNumber == 1;
    }

    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if( targetTile.getLevel() - currentTile.getLevel() < 2) {
            return (board.isTileEmpty(targetTile));
        }

        return false;
    }

    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        return !targetTile.isDome() && targetTile.getWorker() == null;
    }

    public boolean checkWin(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        return targetTile.getLevel() == 3;
    }

    public void move(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        Worker worker = currentTile.getWorker();
        worker.setPosition(targetTile.getPosition());
        targetTile.setWorker(worker);
        currentTile.setWorker(null);
    }

    public void build(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        targetTile.setLevel(targetTile.getLevel() + 1);
    }

}
