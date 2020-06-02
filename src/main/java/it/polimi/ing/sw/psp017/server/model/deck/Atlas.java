package it.polimi.ing.sw.psp017.server.model.deck;


import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Step;
import it.polimi.ing.sw.psp017.server.model.Tile;

/**
 * Atlas' card
 * Titan Shouldering the Heavens
 * Your Build: Your Worker may
 * build a dome at any level.
 */
public class Atlas extends BaseCard{


    /**
     * this method allow worker to build a construction,
     * if the player has set isPowerActive == true , worker can also directly build a dome
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board  main board used to manage the game
     */
     @Override
    public void build(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        if(currentStep.isPowerActive()){
            targetTile.setDome(true);
        }
        else super.build(currentStep,previousStep,board);
    }


    /**
     * Atlas' power is valid only in the second step of the turn which allows him to build directly a dome
     *
     * @param stepNumber step of the current turn
     * @return true if Atlas' power is activated in that specif stepNumber
     */
    public boolean hasChoice(int stepNumber){
        return stepNumber == 1;
    }

}


