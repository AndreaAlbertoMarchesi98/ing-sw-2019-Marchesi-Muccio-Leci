package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Tile;

public class Prometheus extends BaseCard {

    private boolean hasMoved;
    private boolean canMoveUp;
    private int stepCounter;

    @Override
    public boolean canMove(int step) {
        switch (step) {
            case 0:
                return true;
            case 1:
                return !hasMoved;
            default:
                return false;
        }
    }

    @Override
    public boolean canBuild(int step) {
        switch (step) {
            case 0:
                return true;
            case 1:
            case 2:
                return hasMoved;
            default:
                return false;
        }
    }

    @Override
    public boolean isValidMove(Tile currentTile, Tile targetTile) {
        if(canMoveUp)
            return super.isValidMove(currentTile, targetTile);
        else{
            if(targetTile.getLevel()>currentTile.getLevel())
                return false;
            else
                return super.isValidMove(currentTile, targetTile);
        }
    }

    @Override
    public void move(Tile currentTile, Tile targetTile) {
        super.move(currentTile, targetTile);
        canMoveUp = true;
        hasMoved=true;
        stepCounter++;
    }

    @Override
    public void build(Tile targetTile) {
        if (stepCounter == 0)
            canMoveUp = false;
        else
            canMoveUp = true;
        super.build(targetTile);
        hasMoved=false;
        stepCounter++;
    }
}
