package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ActionNames;

import java.io.Serializable;


public class BoardMessage implements Serializable {
    public  PrintableTile[][] board;

    public boolean hasChoice;
    public int activePlayerNumber;
    public boolean[][] validTiles;
    public ActionNames action;

    public BoardMessage(Game game, boolean[][] validTiles, ActionNames action){
        activePlayerNumber = game.getTurn().getPlayerIndex() + 1;
        hasChoice = game.getTurn().hasChoice();
        this.board = new PrintableTile[Board.size][Board.size];
        for(int x = 0; x < Board.size; x++){
            for(int y = 0; y < Board.size; y++){
                this.board[x][y] = new PrintableTile(game.getBoard().getTile(new Vector2d(x, y)));
            }
        }

        this.validTiles = validTiles;
        this.action = action;
    }

    public class PrintableTile implements Serializable{
        public int level;
        public boolean dome;
        public int playerNumber;

        public PrintableTile(Tile tile) {
            this.level = tile.getLevel();
            this.dome = tile.isDome();
            if(tile.getWorker()!=null)
                this.playerNumber = tile.getWorker().getOwner().getPlayerIndex() + 1;
        }
    }
}
