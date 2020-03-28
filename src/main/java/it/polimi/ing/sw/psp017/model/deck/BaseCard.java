package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class BaseCard implements Card{


    private String name;
    //description
    private boolean isOutsideBoard(BoardPosition targetTilePosition){
        return targetTilePosition.x >= 0 && targetTilePosition.y >= 0 && targetTilePosition.x < Game.SIZE_BOARD && targetTilePosition.y < Game.SIZE_BOARD;

    }

    /**
     * This method check if player's move is allowed
     * @param direction possible move direction
     * @param worker to move
     * @return if this move is valid
     */

    @Override
    public boolean isValidMove(BoardPosition direction, Worker worker) {
        BoardPosition targetTilePosition = BoardPosition.sumPositions(worker.getPosition(),direction);
        Tile targetTile = Game.getBoard()[targetTilePosition.x][targetTilePosition.y];
        Tile currentTile = Game.getBoard()[worker.getPosition().x][worker.getPosition().y];
        if(targetTile.isDome() || targetTile.getWorker() != null || isOutsideBoard(targetTilePosition)){
            return false;
        }
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    /**
     *This method check if worker can build in that direction
     * @param direction possible direction
     * @param worker position
     * @return if building is valid
     */

    @Override
    public boolean isValidBuilding(BoardPosition direction, Worker worker) {
        BoardPosition targetTilePosition = BoardPosition.sumPositions(worker.getPosition(),direction);
        Tile targetTile = Game.getBoard()[targetTilePosition.x][targetTilePosition.y];

        return !targetTile.isDome() && targetTile.getWorker() == null;

    }

    /**
     * This method check if the player has won
     * @param worker
     * @return  if the player has won
     */

    @Override
    public boolean checkWin(Worker worker) {
        return Game.getBoard()[worker.getPosition().x][worker.getPosition().y].getLevel() == 3;
    }


    public void move(BoardPosition direction, Worker worker){
        Tile currentTile = Game.getBoard()[worker.getPosition().x][worker.getPosition().y];
        BoardPosition targetTilePosition = BoardPosition.sumPositions(worker.getPosition(),direction);
        Tile targetTile = Game.getBoard()[targetTilePosition.x][targetTilePosition.y];

        currentTile.setWorker(null) ;
        worker.setPosition( targetTilePosition);
        targetTile.setWorker(worker);
    }
    //getDescription
}
