package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

public class Atlas extends BaseCard{

    public void build(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        targetTile.setLevel(targetTile.getLevel()+1);
        if(currentStep.isPowerActive()){
            targetTile.setDome(true);
        }
        else super.build(currentStep,previousStep,board);
    }

    public boolean hasChoice(int stepNumber){
        return stepNumber == 1;
    }

}


