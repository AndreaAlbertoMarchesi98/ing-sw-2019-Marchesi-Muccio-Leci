package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;

/**
 * this class contains all the standard skills that each player can use,
 * it will then be extended by the cards of the specific deities of the game
 */
public class BaseCard implements Card {

    protected GodName name;

    //constructor
    public GodName getName() {
        return name;
    }

    /**
     * this method checks if any other opponent's power is activated on the current player's card
     * @return true card is decorated
     */
    public boolean hasDecorator() {
        return false;
    }

    /**
     * this method checks if in that specific step of the turn the current player has the possibility
     * to proceed in a different way
     * @param stepNumber is the step of the current turn
     * @return true if the player has a choice
     */
    public boolean hasChoice(int stepNumber) {
        return false;
    }

    /**
     * this method check if in this specific stepNuber player can move
     *
     * @param stepNumber current step of the turn
     * @param isPowerActive if true it change default game's logic
     * @return true if it is the firs step
     */
    public boolean canMove(int stepNumber, boolean isPowerActive) {
        return stepNumber == 0;
    }

    /**
     * this method check if in this specific stepNuber player can build
     *
     * @param stepNumber current step of the turn
     * @param isPowerActive if true it change default game's logic
     * @return true if it is the second step
     */
    public boolean canBuild(int stepNumber, boolean isPowerActive) {
        return stepNumber == 1;
    }

    /**
     * this method check if the selected target's move is allowed
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return boolean; return false if the selected tile is a dome or occupied by an enemy or if the player
     * is trying to go up two levels
     **/
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if( targetTile.getLevel() - currentTile.getLevel() < 2) {
            return (board.isTileEmpty(targetTile));
        }

        return false;
    }

    /**
     * this method check if the selected target's build is allowed
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return true if there is no dome and no worker on the target tile
     */
    public boolean isValidBuilding(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        return !targetTile.isDome() && targetTile.getWorker() == null;
    }

    /**
     * this method check if any player has go up to level 3 which is the default winning condition
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return true if a player's worker has go up to level 3
     */
    public boolean checkWin(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        return targetTile.getLevel() == 3;
    }

    /**
     * this method deals with the movement step of the selected worker :
     *
     * place the worker in the designated cell
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     */
    public void move(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        Worker worker = currentTile.getWorker();
        worker.setPosition(targetTile.getPosition());
        targetTile.setWorker(worker);
        currentTile.setWorker(null);
    }

    /**
     * this method deals with the building process of the selected worker:
     * increase tile's level by one
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     */
    public void build(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        targetTile.setLevel(targetTile.getLevel() + 1);
    }

}
