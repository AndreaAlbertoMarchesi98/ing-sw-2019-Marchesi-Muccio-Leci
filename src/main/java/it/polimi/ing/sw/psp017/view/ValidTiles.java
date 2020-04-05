package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.Vector2d;

public class ValidTiles {
    public static final int size=3;
    private boolean isMove;//if false isBuilds
    private boolean[][] tiles=new boolean[3][3];
    private Vector2d centerPosition;
    public ValidTiles(boolean isMove, Vector2d centerPosition){
        this.isMove=isMove;
        this.centerPosition=centerPosition;
    }
    public void setTile(boolean value,Vector2d position){
        tiles[position.x][position.y]=value;
    }
}
