package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.deck.BaseCard;
import it.polimi.ing.sw.psp017.view.CLI;

public class TestConfiguration {
      public GameTest game;
     public Player player1;
     public Player player2;
     public Worker worker1;
     public Worker worker2;
     public Step previousStep = null;
     public Step currentStep = null;
     public Tile currentTile;
     public Board board;

     public TestConfiguration(Card card){
         game =  new GameTest();

         //riempiamo i giocator
       player1 = new Player("name1", Player.Color.BLUE);
       player2 = new Player("name2", Player.Color.RED);

         game.addPlayer(player1);
         game.addPlayer(player2);

         //assegnamo le carte
         Card card1 = card;
         Card card2 = new BaseCard();
         //player1.setCard(card1);
         //player2.setCard(card2);
         worker1 = new Worker(player1);
         worker2 = new Worker(player2);


         //si sistema gli workers
          board = game.getBoard();
         board.addWorker(worker1, new Vector2d(0, 0));
         board.addWorker(worker2, new Vector2d(1, 0));


         currentTile = worker1.getCurrentTile(game.getBoard());
     }




}
