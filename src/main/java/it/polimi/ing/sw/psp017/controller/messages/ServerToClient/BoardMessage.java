package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import  it.polimi.ing.sw.psp017.model.*;

import java.io.Serializable;


public class BoardMessage implements Serializable {
    public  PrintableTile[][] board;

    public boolean hasChoice;
   // public String activePlayer;

    public int activePlayer;

    public BoardMessage(Board board, Game.Turn turn){
        //activePlayer = turn.getActivePlayer().getNickname();

        activePlayer = 0; //da cambiare
        hasChoice = turn.hasChoice();
        this.board = new PrintableTile[Board.size][Board.size];
        for(int x = 0; x < Board.size; x++){
            for(int y = 0; y < Board.size; y++){
                this.board[x][y] = new PrintableTile(board.getTile(new Vector2d(x, y)));
            }
        }
    }

    public class PrintableTile {
        public int level;
        public boolean dome;
        //String player;
        public int playerIndex;

        public PrintableTile(Tile tile) {
            this.level = tile.getLevel();
            this.dome = tile.isDome();
            //if(tile.getWorker()!=null)
                //this.playerIndex = tile.getWorker().getOwner().getNickname();
        }
    }
}
