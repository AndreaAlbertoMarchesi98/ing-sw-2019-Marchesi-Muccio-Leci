package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;
import it.polimi.ing.sw.psp017.model.deck.Athena;

public class AthenaDecorator extends CardDecorator {

    Athena athenaCard;

    public AthenaDecorator(Card newCard, Card referenceCard) {
        super(newCard, referenceCard);
        athenaCard = (Athena) referenceCard;
    }

    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        if (athenaCard.hasMovedUp()) {
            if (currentStep.getTargetTile().getLevel() > currentStep.getCurrentTile().getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }
        else
            return super.isValidMove(currentStep, previousStep, board);
    }
}