package it.polimi.ing.sw.psp017;
import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;

import static org.junit.Assert.assertEquals;

public class CommonTest {


    public static void commonTest(GodName godName) {
        GameTest game = new GameTest();

        //riempiamo i giocatori

        Player player1 = new Player("name1");
        Player player2 = new Player("name2");

        game.addPlayer(player1);
        game.addPlayer(player2);

        //assegnamo le carte
        Card card = CardFactory.getCard(godName);
        //Card card2 = new BaseCard();
        player1.setCard(card);
        //player2.setCard(card2);

        //si sistema gli workers
        Board board = game.getBoard();
        Vector2d positionWorker1 = new Vector2d(0, 0);
        Vector2d positionWorker2 = new Vector2d(1, 0);
        Worker worker1 = new Worker(player1);
        Worker worker2 = new Worker(player2);
        board.addWorker(worker1, positionWorker1);
        board.addWorker(worker2, positionWorker2);
       // player1.addWorker(worker1);
        //player2.addWorker(worker2);


        //si gioca
        Step previousStep = null;
        Step currentStep;

        Player player = player1;
        Tile workerTile = player.getWorkers().get(0).getCurrentTile(board);
        currentStep = new Step(workerTile, null, false);

        boolean isPowerActive = false;
        int stepNumber = 0;
        //while (!Server.isTurnFinished(player, stepNumber, isPowerActive)) {

            if (card.hasChoice(stepNumber)) {
                isPowerActive = true;
            }
            currentStep.setPowerActive(isPowerActive);
            //CLI GUI stuff
            if (card.canMove(stepNumber, isPowerActive)) {

                // ValidTiles validTiles=Server.calculateValidMoves(currentStep,previousStep,board);
                //CLI.printValidTiles(validTiles,board);
                Tile targetTile = game.getBoard().getTile(Vector2d.sumVectors(positionWorker1, new Vector2d(0, 1)));
                currentStep.setTargetTile(targetTile);
                if(card.isValidMove(currentStep,previousStep,board)){
                    card.move(currentStep, previousStep, board);
                    assertEquals(targetTile, worker1.getCurrentTile(board));
                }

            } else if (card.canBuild(stepNumber, isPowerActive)) {//in futuro da cancellare perche ridondante


                Tile targetTile = game.getBoard().getTile(Vector2d.sumVectors(positionWorker1, new Vector2d(0, 1)));
                currentStep.setTargetTile(targetTile);
                if(card.isValidBuilding(currentStep,previousStep,board)) {
                    card.build(currentStep, previousStep, board);
                    assertEquals(1, targetTile.getLevel());
                }
            }
            previousStep = currentStep;
            currentStep.setCurrentTile(previousStep.getTargetTile());
            stepNumber++;
      //  }


        // }
    }
}





