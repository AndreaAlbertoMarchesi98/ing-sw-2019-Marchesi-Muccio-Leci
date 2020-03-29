package it.polimi.ing.sw.psp017.model;

public class Vector2d {
    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int x;
    public int y;

    public static Vector2d sumVectors(Vector2d Vector2d1, Vector2d Vector2d2){
        Vector2d bp = new Vector2d(0,0);
        bp.x= Vector2d1.x + Vector2d2.x;
        bp.y=Vector2d1.y + Vector2d2.y;
        return bp;
    }

}
