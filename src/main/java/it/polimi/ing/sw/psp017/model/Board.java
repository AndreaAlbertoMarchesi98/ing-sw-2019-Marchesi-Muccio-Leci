package it.polimi.ing.sw.psp017.model;

public class Board {
        private static Tile[][] tiles;
        public static final int size = 5;

        public static Tile[][] getTiles() {
            return tiles;
        }

        public static void setTiles(Tile[][] tiles) {
            Board.tiles = tiles;
        }

}
