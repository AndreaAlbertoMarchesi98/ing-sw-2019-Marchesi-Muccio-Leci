package it.polimi.ing.sw.psp017.model;

import it.polimi.ing.sw.psp017.view.GodName;

/**
 * this is the interface card which will be implemented by baseCard and extended from each God's card
 */
public interface Card {
    GodName getName();

    boolean hasChoice(int stepNumber);

    boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board);

    boolean canMove(int stepNumber, boolean isPowerActive);

    boolean canBuild(int stepNumber, boolean isPowerActive);

    boolean isValidMove(Step currentStep, Step previousStep, Board board);

    boolean isValidBuilding(Step currentStep, Step previousStep, Board board);

    boolean checkWin(Step currentStep, Step previousStep, Board board);

    void move(Step currentStep, Step previousStep, Board board);

    void build(Step currentStep, Step previousStep, Board board);
}
