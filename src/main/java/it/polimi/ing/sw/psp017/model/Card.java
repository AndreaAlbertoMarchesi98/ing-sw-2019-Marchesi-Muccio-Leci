package it.polimi.ing.sw.psp017.model;
/*ROBE DA CHIEDERE
1)caso minotar
*/
public interface Card {
    boolean hasDecorator();

    boolean hasChoice(int stepNumber);

    boolean canMove(int stepNumber, boolean isPowerActive);

    boolean canBuild(int stepNumber, boolean isPowerActive);

    boolean isValidMove(Step currentStep, Step previousStep, Board board);

    boolean isValidBuilding(Step currentStep, Step previousStep, Board board);

    boolean checkWin(Step currentStep, Step previousStep, Board board);

    void move(Step currentStep, Step previousStep, Board board);

    void build(Step currentStep, Step previousStep, Board board);
}
