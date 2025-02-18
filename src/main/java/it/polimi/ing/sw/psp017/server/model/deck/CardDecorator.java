package it.polimi.ing.sw.psp017.server.model.deck;

import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Card;
import it.polimi.ing.sw.psp017.server.model.Step;

abstract public class CardDecorator implements Card {
    protected Card tempCard;


    public CardDecorator(Card newCard) {
        tempCard = newCard;
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

    public boolean checkWin(Step currentStep, Board board) {
        return tempCard.checkWin(currentStep, board);
    }

    public void move(Step currentStep, Step previousStep, Board board) {
        tempCard.move(currentStep, previousStep, board);
    }

    public void build(Step currentStep, Step previousStep, Board board) {
        tempCard.build(currentStep, previousStep, board);
    }

    public boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board) {
        return tempCard.hasActiveDecorator(currentStep,previousStep,board);
    }

    public Card getDecorator(Card cardToDecorate){
        return tempCard.getDecorator(cardToDecorate);
    }

}
