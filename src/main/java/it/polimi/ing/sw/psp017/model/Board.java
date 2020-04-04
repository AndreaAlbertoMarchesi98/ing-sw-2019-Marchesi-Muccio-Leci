package it.polimi.ing.sw.psp017.model;

public class Board {
    private Tile[][] tiles;
    public static final int size = 5;

    public Tile getTile(Vector2d position) {
        return tiles[position.x][position.y];
    }

    public boolean isTileEmpty(Tile tile){
        return !tile.isDome() && tile.getWorker() == null;
    }

    public static boolean isInsideBounds(Vector2d position) {
        return position.x < size && position.x >= 0 && position.y < size && position.y >= 0;
    }

}
