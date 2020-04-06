package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.deck.Athena;
import it.polimi.ing.sw.psp017.view.GodName;

public class AthenaDecorator extends CardDecorator {

    Athena athenaCard;

    public AthenaDecorator(Card newCard) {
        super(newCard);
    }

    @Override
    public GodName getName() {
        return tempCard.getName();
    }

    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        if (true) {
            if (currentStep.getTargetTile().getLevel() > currentStep.getCurrentTile().getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }
        else
            return super.isValidMove(currentStep, previousStep, board);
    }
}