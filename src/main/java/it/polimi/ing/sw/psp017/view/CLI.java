package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.Board;
import it.polimi.ing.sw.psp017.model.Game;
import it.polimi.ing.sw.psp017.model.Tile;
import it.polimi.ing.sw.psp017.model.Vector2d;

public class CLI {
    void printTile(Tile tile){
        String worker=null;
        if(tile.getWorker()!=null)
            worker=tile.getWorker().toString();
        String level=String.valueOf(tile.getLevel());
        String dome=String.valueOf(tile.isDome());
        if(worker!=null)
            System.out.print("{"+worker+level+dome+"}");
        else
            System.out.print("{"+level+dome+"}");
    }
    public void printBoard(Board board){
        for(int y = 0; y< Board.size; y++){
            for(int x=0;x< Board.size;x++){
                printTile(board.getTile(new Vector2d(x,y)));
            }
            System.out.println();
        }
    }
}
