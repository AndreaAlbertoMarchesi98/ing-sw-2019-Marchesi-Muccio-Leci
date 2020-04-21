package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.ActionMessage;
import it.polimi.ing.sw.psp017.model.*;

import java.util.Scanner;

public class CLI {
    private Scanner input = new Scanner(System.in);

    public static void printTile(Tile tile){
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

    public static Tile getTile(Board board){
        //controllare se è nei bouds
        return new Tile(new Vector2d(0,0));
    }
    public static void printValidTiles(ValidTiles validTiles, Board board){
        //controllare se è nei bouds
    }
    public static Tile getWorkerTile(Board board, Player player){
        //controllare se è nei bouds
        return new Tile(new Vector2d(0,0));
    }
    public static Tile getTargetTile(Board board, ValidTiles validTiles){
        //controllare se è una valid tile
        return new Tile(new Vector2d(0,0));
    }
}


