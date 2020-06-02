package it.polimi.ing.sw.psp017.server.model;

import java.io.Serializable;

public class Vector2d implements Serializable {
    public int x;
    public int y;



    public Vector2d(int x, int y){
        this.x=x;
        this.y=y;
    }

    /**
     *
     * @param vector1 position
     * @param vector2 position
     * @return a new vector which is the sum of the previous one
     */
    public static Vector2d sumVectors(Vector2d vector1, Vector2d vector2){
        Vector2d vector = new Vector2d(0,0);
        vector.x= vector1.x + vector2.x;
        vector.y=vector1.y + vector2.y;
        return vector;
}

    /**
     *
     * @param vector1 position
     * @param vector2 position
     * @return vector , result of the subtraction
     */
    public static Vector2d subtractVectors(Vector2d vector1, Vector2d vector2){
        Vector2d vector = new Vector2d(0,0);
        vector.x= vector1.x - vector2.x;
        vector.y=vector1.y - vector2.y;
        return vector;
    }

}
