package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

public class BoardMessage {
    public  Tile[][] board;

    public boolean hasChoice;
    public boolean isMoving;
    public String activePlayer;

    BoardMessage(Tile[][] board){
        this.board = board;
    }

    public class Tile {
        int level;
        boolean dome;
        int player;


        public Tile(int level, boolean dome, int player) {
            this.level = level;
            this.dome = dome;
            this.player = player;
        }
    }
}
