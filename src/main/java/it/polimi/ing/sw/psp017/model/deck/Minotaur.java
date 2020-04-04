package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class Minotaur extends BaseCard {

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

    @Override
    public void move(Step currentStep, Step previousStep, Board board) {
        Tile targetTile = currentStep.getTargetTile();
        if(targetTile.getWorker()!=null)
            knockOutWorker(currentStep, board);
        else
            super.move(currentStep, previousStep, board);
    }

    private Vector2d calculatePushPosition(Vector2d currentPosition, Vector2d targetPosition) {
        Vector2d direction = Vector2d.subtractVectors(targetPosition, currentPosition);
        return Vector2d.sumVectors(targetPosition,direction);
    }

    private boolean canKnockOutWorker(Step currentStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Tile targetTile = currentStep.getTargetTile();

        Vector2d pushPosition = calculatePushPosition(currentTile.getPosition(), targetTile.getPosition());

        return Board.isInsideBounds(pushPosition) && board.isTileEmpty(board.getTile(pushPosition));
    }

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
