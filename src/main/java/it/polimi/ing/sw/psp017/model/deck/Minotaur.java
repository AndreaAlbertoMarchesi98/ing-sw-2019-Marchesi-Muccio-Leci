package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

/**
 * Minotaur's card
 *
 * Bull-headed Monster
 * Your Move: Your Worker may
 * move into an opponent Worker’s
 * space, if their Worker can be
 * forced one space straight backwards to an
 * unoccupied space at any level.
 */
public class Minotaur extends BaseCard {

    /**
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     * @return true if targetTile selected is a valid move
     */
    @Override
    public boolean isValidMove(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        if (targetTile.getWorker() != null) {
            if (targetTile.getWorker().getOwner() != currentTile.getWorker().getOwner())
                return canKnockOutWorker(currentStep, board);
            else
                return false;
        } else
            return super.isValidMove(currentStep, previousStep, board);
    }

    /**
     * if targetTile has an enemy worker than
     * minotaur's power is applied otherwise default move is applied
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param previousStep contains information about last step
     * @param board main board used to manage the game
     */
    @Override
    public void move(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        if(targetTile.getWorker()!=null)
            knockOutWorker(currentStep, board);
        else
            super.move(currentStep, previousStep, board);
    }

    /**
     * this method calculate the position where the enemyWorker will be pushed
     *
     * @param currentPosition contain current worker's position in the board game
     * @param targetPosition contain current worker's target position in the board game
     * @return a vector position which is
     */
    private Vector2d calculatePushPosition(Vector2d currentPosition, Vector2d targetPosition) {
        Vector2d direction = Vector2d.subtractVectors(targetPosition, currentPosition);
        return Vector2d.sumVectors(targetPosition,direction);
    }

    /**
     * this method check if minotauros's worker can push opponet's worker calculating the destination's tile
     *
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param board main board used to manage the game
     * @return true if the destination tile is inside the board and empty
     */
    private boolean canKnockOutWorker(Step currentStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();

        Vector2d pushPosition = calculatePushPosition(currentTile.getPosition(), targetTile.getPosition());

        return Board.isInsideBounds(pushPosition) && board.isTileEmpty(board.getTile(pushPosition));
    }

    /**
     *this method change workers' position in the board game
     *worker goes to enemy's position and enemyWorker goes to a new tile calculated with calculatePushPosition
     *
     * @param currentStep contains information about in which tile the worker is and what move wants to do next
     * @param board main board used to manage the game
     */
    private void knockOutWorker(Step currentStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();
        Vector2d pushPosition = calculatePushPosition(currentTile.getPosition(), targetTile.getPosition());
        Tile pushTile = board.getTile(pushPosition);

        Worker enemyWorker = targetTile.getWorker();
        Worker worker = currentTile.getWorker();

        pushTile.setWorker(enemyWorker);
        targetTile.setWorker(worker);
        currentTile.setWorker(null);

        enemyWorker.setPosition(pushPosition);
        worker.setPosition(currentTile.getPosition());
    }
}
