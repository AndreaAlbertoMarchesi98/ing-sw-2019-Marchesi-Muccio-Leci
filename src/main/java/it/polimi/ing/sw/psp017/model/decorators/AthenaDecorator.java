package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Step;

public class AthenaDecorator extends CardDecorator {

    public AthenaDecorator(Card newCard) {
        super(newCard);
    }


    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        if (currentStep.getTargetTile().getLevel() > currentStep.getCurrentTile().getLevel())
            return false;
        else
            return super.isValidMove(currentStep, previousStep, board);
    }


}