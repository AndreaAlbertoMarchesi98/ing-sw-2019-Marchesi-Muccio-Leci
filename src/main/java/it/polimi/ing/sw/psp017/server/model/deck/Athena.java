package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Card;
import it.polimi.ing.sw.psp017.server.model.Step;

public class Athena extends BaseCard {


    @Override
    public boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board) {
        if(previousStep!=null) {
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


            System.out.println("current "+currentStep.getCurrentTile().getLevel());
            System.out.println("target "+ currentStep.getTargetTile().getLevel());
            if (currentStep.getTargetTile().getLevel() > currentStep.getCurrentTile().getLevel())
                return false;
            else
                return super.isValidMove(currentStep, previousStep, board);
        }


    }

}
