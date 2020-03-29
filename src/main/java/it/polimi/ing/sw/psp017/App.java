package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GodName;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Game game = Game.getInstance();
        System.out.println(Game.Board.getTiles()[0][0].getWorker());
        Worker pedina = new Worker(new Player(), new Vector2d(0,0));
        Card card;
        Tile currentTile;
        Tile targetTile;


        try {
            card=CardFactory.getCard(GodName.APOLLO);
            currentTile = pedina.getCurrentTile();
            targetTile = pedina.getTargetTile(new Vector2d(0, 1));

            currentTile.setWorker(pedina);

            if(card.checkWin(currentTile,targetTile)) System.out.println("win");
            card.isValidMove(currentTile,targetTile);
            card.move(currentTile,targetTile);
            if(!card.isValidBuilding(targetTile))
                System.out.println("not valid building");
            card.build(targetTile);
        }
        catch(NullPointerException e){
            System.out.println("wtf");
            e.printStackTrace();
        }

        System.out.println(Game.Board.getTiles()[0][0].getWorker());
        System.out.println(Game.Board.getTiles()[0][1].getWorker());

        CLI cli = new CLI();
        cli.printBoard();
    }

}
