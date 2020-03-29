package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.model.Game;
import it.polimi.ing.sw.psp017.model.Tile;

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
    public void printBoard(){
        for(int y=0; y< Game.Board.size;y++){
            for(int x=0;x<Game.Board.size;x++){
                printTile(Game.Board.getTiles()[x][y]);
            }
            System.out.println();
        }
    }
}
