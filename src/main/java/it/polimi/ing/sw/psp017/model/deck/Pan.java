package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Step;
import it.polimi.ing.sw.psp017.model.deck.BaseCard;

/**
 * Pan's card
 *
 * God of the Wild
 * Win Condition: You also win if
 * your Worker moves down two or
 * more levels.
 */
public class Pan extends BaseCard {

    /**
     *
     * in addition to the normal chance of winning, this method also adds that of Pan:
     * if the difference between the starting and finishing cell is greater than 2,
     * the player has automatically won
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param board main board used to manage the game
     * @return true is current player has won
     */
    @Override
    public boolean checkWin(Step currentStep, Board board) {
            if(currentStep.getCurrentTile().getLevel()-currentStep.getTargetTile().getLevel() >=2) return true;
        else return super.checkWin(currentStep, board);

    }
}
