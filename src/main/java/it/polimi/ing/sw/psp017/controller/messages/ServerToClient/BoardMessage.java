package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.model.*;

public class BoardMessage {
    public  PrintableTile[][] board;

    public boolean hasChoice;
    public String activePlayer;

    public BoardMessage(Board board, Game.Turn turn){
        activePlayer = turn.getActivePlayer().getNickname();
        hasChoice = turn.hasChoice();
        this.board = new PrintableTile[Board.size][Board.size];
        for(int x = 0; x < Board.size; x++){
            for(int y = 0; y < Board.size; y++){
                this.board[x][y] = new PrintableTile(board.getTile(new Vector2d(x, y)));
            }
        }
    }

    public class PrintableTile {
        int level;
        boolean dome;
        String player;

        public PrintableTile(Tile tile) {
            this.level = tile.getLevel();
            this.dome = tile.isDome();
            if(tile.getWorker()!=null)
                this.player = tile.getWorker().getOwner().getNickname();
        }
    }
}
