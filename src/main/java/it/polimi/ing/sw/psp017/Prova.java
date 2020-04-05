package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.Server;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.model.decorators.AthenaDecorator;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.ValidTiles;

import javax.swing.*;
import java.util.ArrayList;

public class Prova {


    public static void main(String[] args) {
        Game game = Game.getInstance();

        //riempiamo i giocatori
        System.out.println(CLI.getNickname());
        Player player1 = new Player("name1", Player.Color.BLUE);
        Player player2 = new Player("name2", Player.Color.RED);

        game.addPlayer(player1);
        game.addPlayer(player2);

        //assegnamo le carte
        Card card1 = CardFactory.getCard(GodName.ATLAS);
        Card card2 = CardFactory.getCard(GodName.PAN);
        player1.setCard(card1);
        player2.setCard(card2);

        //si sistema gli workers
        Board board = game.getBoard();
        board.addWorker(new Worker(player1), new Vector2d(0, 0));
        board.addWorker(new Worker(player2), new Vector2d(1, 0));


        //si gioca
        Step previousStep = null;
        Step currentStep = null;
        Card card;
        while (!game.isGameOver()) {
            for (Player player : game.getPlayers()) {
                card=player.getCard();
                for (Player otherPlayer : game.getPlayers()) {
                    Card otherCard=otherPlayer.getCard();
                    if(!player.equals(otherPlayer)&&otherCard.hasDecorator()){
                        card=CardFactory.getDecorator(otherCard.getName(),card);
                    }
                }

                boolean isPowerActive = false;
                int stepNumber = 0;
                while (!Server.isTurnFinished(player, stepNumber, isPowerActive)) {
                    if (card.hasChoice(stepNumber)) {
                        //CLI GUI
                        isPowerActive=CLI.getChoice();
                    }
                    //CLI GUI stuff
                    if(card.canMove(stepNumber,isPowerActive)){
                        Tile workerTile = CLI.getWorkerTile(board, player);
                        currentStep=new Step(workerTile,null,isPowerActive);
                        ValidTiles validTiles=Server.calculateValidMoves(currentStep,previousStep,board);
                        CLI.printValidTiles(validTiles,board);
                        Tile targetTile = CLI.getTargetTile(board, validTiles);
                        currentStep.setTargetTile(targetTile);
                        card.move(currentStep,previousStep,board);
                    }
                    else if(card.canBuild(stepNumber,isPowerActive)){//in futuro da cancellare perche ridondante
                        Tile workerTile = CLI.getWorkerTile(board, player);
                        currentStep=new Step(workerTile,null,isPowerActive);
                        ValidTiles validTiles=Server.calculateValidBuilds(currentStep,previousStep,board);
                        CLI.printValidTiles(validTiles,board);
                        Tile targetTile = CLI.getTargetTile(board, validTiles);
                        currentStep.setTargetTile(targetTile);
                        card.build(currentStep,previousStep,board);
                    }
                    previousStep = currentStep;
                    stepNumber++;
                }
            }
        }
    }
}





    /*///CODE CEMETERY
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
            Player player;
            boolean isPowerActivated;
            while(card.canMove(turn)||card.canBuild(turn)){
                Step = new Step(ojjmoolmj,isPowerActivated);
                if(card.hasChoice(turn))
                    isPowerActivated=button.value;
                    //ui stuff button listener
                    //step.setIsPwerActive(button.toggle)
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
*/

