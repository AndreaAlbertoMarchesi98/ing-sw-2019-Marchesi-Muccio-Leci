package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Step;

public class Athena extends BaseCard {


    @Override
    public boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board) {
        if(currentStep != null) {
            return previousStep.getTargetTile().getLevel() > previousStep.getCurrentTile().getLevel();
        } else return false;
    }

    @Override
    public Card getDecorator(Card cardToDecorate) {
        return new AthenaDecorator(cardToDecorate);
    }


    class AthenaDecorator extends CardDecorator {

        public AthenaDecorator(Card newCard) {
            super(newCard);
        }

        @Override
        public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
            System.out.println("using Athena decorator Effect");
            if (currentStep.getTargetTile().getLevel() > currentStep.getCurrentTile().getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }


    }

}
