package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.Server;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GodName;

public class Prova {


    public static void main(String[] args) {
        Game game = Game.getInstance();

        //riempiamo i giocatori
       // System.out.println(CLI.getNickname());
        Player player1 = new Player("name1", Player.Color.BLUE);
        Player player2 = new Player("name2", Player.Color.RED);

        game.addPlayer(player1);
        game.addPlayer(player2);

        //assegnamo le carte
        Card card1 = CardFactory.getCard(GodName.ARTEMIS);
        Card card2 = CardFactory.getCard(GodName.DEMETER);
        player1.setCard(card1);
        player2.setCard(card2);

        //si sistema gli workers
        Board board = game.getBoard();
        Vector2d positionWorker1 = new Vector2d(0, 0);
        Vector2d positionWorker2=new Vector2d(1, 0);
        Worker worker1 = new Worker(player1);
        Worker worker2 = new Worker(player2);
        board.addWorker(worker1, positionWorker1);
        board.addWorker(worker2, positionWorker2);
        player1.addWorker(worker1);
        player2.addWorker(worker2);



        //si gioca
        Step previousStep = null;
        Step currentStep;

        Card card;
       // while (!game.isGameOver()) {
            for (Player player : game.getPlayers()) {
                card=player.getCard();
                Tile workerTile =player.getWorkers().get(0).getCurrentTile(board);
                currentStep=new Step(workerTile,null,false);
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
                    currentStep.setPowerActive(isPowerActive);
                    //CLI GUI stuff
                    if(card.canMove(stepNumber,isPowerActive)){

                       // ValidTiles validTiles=Server.calculateValidMoves(currentStep,previousStep,board);
                        //CLI.printValidTiles(validTiles,board);
                        Tile targetTile =game.getBoard().getTile(new Vector2d(0, 1));
                        currentStep.setTargetTile(targetTile);
                        card.move(currentStep,previousStep,board);
                    }
                    else if(card.canBuild(stepNumber,isPowerActive)){//in futuro da cancellare perche ridondante


                        Tile targetTile =game.getBoard().getTile(new Vector2d(1, 1));
                        currentStep.setTargetTile(targetTile);
                        card.build(currentStep,previousStep,board);
                    }
                    previousStep = currentStep;
                    currentStep.setCurrentTile(previousStep.getTargetTile());
                    stepNumber++;
                }
            }
       // }
    }
}





