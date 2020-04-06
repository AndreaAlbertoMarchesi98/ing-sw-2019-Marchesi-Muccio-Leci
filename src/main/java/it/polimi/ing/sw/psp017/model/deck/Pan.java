package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;

public class Pan extends BaseCard{
    @Override
    public boolean checkWin(Step currentStep, Step previousStep, Board board) {
            if(currentStep.getCurrentTile().getLevel()-currentStep.getTargetTile().getLevel() >=2) return true;
        else return super.checkWin(currentStep, previousStep, board);

    }
}
