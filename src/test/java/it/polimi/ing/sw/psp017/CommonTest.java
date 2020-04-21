package it.polimi.ing.sw.psp017;
import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.server.Server1;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;

import static org.junit.Assert.assertEquals;

public class CommonTest {


    public static void commonTest(GodName godName) {
        GameTest game = new GameTest();

        //riempiamo i giocatori

        Player player1 = new Player("name1", Player.Color.BLUE);
        Player player2 = new Player("name2", Player.Color.RED);

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
        while (!Server1.isTurnFinished(player, stepNumber, isPowerActive)) {

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
        }


        // }
    }
}





//CODE CEMETERY
    /*    Game game = Game.getInstance();
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

