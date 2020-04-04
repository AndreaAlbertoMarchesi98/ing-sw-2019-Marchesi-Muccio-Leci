package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.Tile;

abstract public class CardDecorator implements Card {
    protected Card tempCard;
    protected Card referenceCard;


    public CardDecorator(Card newCard, Card referenceCard) {
        tempCard = newCard;
        this.referenceCard = referenceCard;
    }


    public boolean hasChoice(int stepNumber){
        return tempCard.hasChoice(stepNumber);
    }

    public boolean canMove(int stepNumber, boolean isPowerActive){
        return tempCard.canMove(stepNumber, isPowerActive);
    }

    public boolean canBuild(int stepNumber, boolean isPowerActive){
        return tempCard.canBuild(stepNumber, isPowerActive);
    }

    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        return tempCard.isValidMove(currentStep, previousStep, board);
    }

    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        return tempCard.isValidBuilding(currentStep, previousStep, board);
    }

    public boolean checkWin(Step currentStep, Step previousStep, Board board) {
        return tempCard.checkWin(currentStep, previousStep, board);
    }

    public void move(Step currentStep, Step previousStep, Board board) {
        tempCard.move(currentStep, previousStep, board);
    }

    public void build(Step currentStep, Step previousStep, Board board) {
        tempCard.build(currentStep, previousStep, board);
    }

    public boolean hasDecorator() {
        return tempCard.hasDecorator();
    }

}
