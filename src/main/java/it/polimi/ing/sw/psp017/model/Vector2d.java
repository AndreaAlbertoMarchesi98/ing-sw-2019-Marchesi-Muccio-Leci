package it.polimi.ing.sw.psp017.model;

public class Vector2d {
    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }
    public int x;
    public int y;

    public static Vector2d sumVectors(Vector2d vector1, Vector2d vector2){
        Vector2d vector = new Vector2d(0,0);
        vector.x= vector1.x + vector2.x;
        vector.y=vector1.y + vector2.y;
        return vector;
}
    public static Vector2d subtractVectors(Vector2d vector1, Vector2d vector2){
        Vector2d vector = new Vector2d(0,0);
        vector.x= vector1.x - vector2.x;
        vector.y=vector1.y - vector2.y;
        return vector;
    }

}
