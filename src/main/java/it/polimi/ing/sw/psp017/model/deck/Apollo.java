package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

public class Apollo extends BaseCard {
    @Override
    public boolean isValidMove(BoardPosition direction, Worker worker) {
        BoardPosition targetTilePosition = BoardPosition.sumPositions(worker.getPosition(),direction);
        Tile targetTile = Game.getBoard()[targetTilePosition.x][targetTilePosition.y];
        Tile currentTile = Game.getBoard()[worker.getPosition().x][worker.getPosition().y];
        if(targetTile.isDome() || targetTile.getWorker().getOwner() == worker.getOwner()){
            return false;
        }
        return targetTile.getLevel() - currentTile.getLevel() >= 2;
    }

    @Override
    public void move(BoardPosition direction, Worker worker){
        Worker tempWorker ;
        BoardPosition targetTilePosition = BoardPosition.sumPositions(worker.getPosition(),direction);
        BoardPosition currentTilePosition = worker.getPosition();
        Tile targetTile = Game.getBoard()[targetTilePosition.x][targetTilePosition.y];
        Tile currentTile = Game.getBoard()[worker.getPosition().x][worker.getPosition().y];
        if(targetTile.getWorker() == null)
            super.move(direction,worker);

        else {
            tempWorker= targetTile.getWorker();
            targetTile.setWorker(worker);
            currentTile.setWorker(tempWorker);
            targetTile.getWorker().setPosition(targetTilePosition);
            currentTile.getWorker().setPosition(currentTilePosition);

        }

    }



    }
