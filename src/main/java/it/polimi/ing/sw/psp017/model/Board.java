package it.polimi.ing.sw.psp017.model;

public class Board {
    private Tile[][] tiles;
    public static final int size = 5;

    public Board(){
        tiles = new Tile[Board.size][Board.size];
        for (int x = 0; x < Board.size; x++) {
            for (int y = 0; y < Board.size; y++) {
                tiles[x][y]=new Tile(new Vector2d(x,y));
            }
        }
    }
    public void addWorker(Worker worker, Vector2d position){
        Tile tile = getTile(position);
        tile.setWorker(worker);
        worker.setPosition(position); //riga aggiunta
    }

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
