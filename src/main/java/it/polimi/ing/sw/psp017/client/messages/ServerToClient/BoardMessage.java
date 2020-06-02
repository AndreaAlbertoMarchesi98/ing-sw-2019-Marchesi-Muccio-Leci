package it.polimi.ing.sw.psp017.client.messages.ServerToClient;

import it.polimi.ing.sw.psp017.client.view.ActionNames;
import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Game;
import it.polimi.ing.sw.psp017.server.model.Tile;
import it.polimi.ing.sw.psp017.server.model.Vector2d;

import java.io.Serializable;


public class BoardMessage implements Serializable {
    public  PrintableTile[][] board;

    public boolean hasChoice;
    public int activePlayerNumber;
    public boolean[][] validTiles;
    public ActionNames action;






    public BoardMessage(Game game){
        System.out.println(game.getAction());
        activePlayerNumber = game.getPlayerIndex() + 1;
        hasChoice = game.hasChoice();
        this.board = new PrintableTile[Board.size][Board.size];
        for(int x = 0; x < Board.size; x++){
            for(int y = 0; y < Board.size; y++){
                this.board[x][y] = new PrintableTile(game.getBoard().getTile(new Vector2d(x, y)));
            }
        }
        this.validTiles = game.getValidTiles();
        this.action = game.getAction();

        for(int x = 0; x < Board.size; x++){
            for(int y = 0; y < Board.size; y++){
                if(this.board[x][y].dome)
                    System.out.println(this.board[x][y].dome);
            }
        }

    }

    public class PrintableTile implements Serializable{
        public int level;
        public boolean dome;
        public int playerNumber;

        public PrintableTile(Tile tile) {
            this.level = tile.getLevel();
            this.dome = tile.isDome();
            if(tile.getWorker()!=null)
                this.playerNumber = tile.getWorker().getOwner().getPlayerNumber();
        }

    }
}
