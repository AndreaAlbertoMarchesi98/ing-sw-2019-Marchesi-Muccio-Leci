package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class ValidTiles {
    
    public static final int size=3;
    private boolean isMove;//if false isBuilds
    private boolean[][] tiles=new boolean[3][3];
    private Vector2d bottomLeftPosition;
    public ValidTiles(boolean isMove, Vector2d bottomLeftPosition){
        this.isMove=isMove;
        this.bottomLeftPosition=bottomLeftPosition;
    }
    public void setTile(boolean value,Vector2d position){
        tiles[position.x][position.y]=value;
    }

    public static int getSize() {
        return size;
    }

    public boolean isMove() {
        return isMove;
    }

    public boolean[][] getTiles() {
        return tiles;
    }

    public Vector2d getBottomLeftPosition() {
        return bottomLeftPosition;
    }
    public static ValidTiles createEmptyValidTiles(){
        return new ValidTiles(false, new Vector2d(0,0));
    }
}
