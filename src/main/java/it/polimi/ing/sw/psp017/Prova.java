package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.model.decorators.AthenaDecorator;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GodName;

import javax.swing.*;
import java.util.ArrayList;

public class Prova
{




    
    public static void main( String[] args )
    {
        Game game = Game.getInstance();
        Player athenaPlayer=new Player();
        game.setPlayers(new ArrayList<Player>());
        System.out.println(Game.Board.getTiles()[0][0].getWorker());
        Worker pedina1 = new Worker(new Player(), new Vector2d(0,0));
        Worker pedina2 = new Worker(new Player(), new Vector2d(0,2));
        Card card;
        Tile currentTile;
        Tile moveTile;
        Tile buildTile;


        try {
            card=CardFactory.getCard(GodName.APOLLO);

            int turn=0;
            while(card.canMove(turn)||card.canBuild(turn)){
                //move or build ui and stuff
                turn++;
            }

            //controller decorator

            for (Player player : game.getPlayers()) {
                if(player.getCard().hasDecorator()){
                    for (Player otherPlayer : game.getPlayers()) {
                        if(!player.equals(otherPlayer)){
                            otherPlayer.setCard(new AthenaDecorator(otherPlayer.getCard(),player.getCard()));
                        }
                    }
                }
            }


            currentTile = pedina1.getCurrentTile();
            moveTile = pedina1.getTargetTile(new Vector2d(0, 1));
            buildTile= pedina1.getTargetTile(new Vector2d(0, 0));

            currentTile.setWorker(pedina1);

            if(card.checkWin(currentTile,moveTile)) System.out.println("win");
            card.isValidMove(currentTile,moveTile);


            if(!card.isValidBuilding(buildTile))
                System.out.println("not valid building");
            card.build(buildTile);
        }
        catch(NullPointerException e){
            System.out.println("wtf");
            e.printStackTrace();
        }

        CLI cli = new CLI();
        cli.printBoard();
    }

}
