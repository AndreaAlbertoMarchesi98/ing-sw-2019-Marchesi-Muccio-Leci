package it.polimi.ing.sw.psp017;

import static org.junit.Assert.assertTrue;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.model.deck.BaseCard;
import it.polimi.ing.sw.psp017.model.deck.Pan;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Target;


public class AppTest 
{
    Game game = Game.getInstance();
    Card card;
    Step stepcurrent ;
    Step stepprevious;
    Player p1 ;
    Worker w1,w2;
    Board board;

    @Before
    public void before() throws Exception{
        board = new Board();  //no sense
        p1 = new Player();
        game.addPlayer(p1);
        w1 = new Worker(p1, new Vector2d(0,0)); //mettere nel costruttore la board del worker o nel player
        p1.addWorker(w1);  //attenta qua a come w1 e p1 si referenziano tra loro


    }

    @Test
    public void PanTest(){
        card = new Pan();
        stepcurrent = new Step();
        stepprevious = new Step();
        Tile currentTile = new Tile(new Vector2d(0,0));
        currentTile.setLevel(2);
        Tile targertTile = new Tile(new Vector2d(0,1));
        stepcurrent.setCurrentTile(currentTile);
        stepcurrent.setTargetTile(targertTile);

        if(card.checkWin(stepcurrent,stepprevious,board)) System.out.println("WIN");
    }
   @Test
    public void TestBaseGame(){
        card = new BaseCard();
        p1.setCard(card);
        stepcurrent = new Step();
        stepprevious = new Step();
        int stepNumber = 0;
       Worker currentWorker = p1.getWorkers().get(0);
        currentWorker.getCurrentTile().setWorker(currentWorker);
       stepcurrent = new Step();
      do{
           stepcurrent.setCurrentTile(currentWorker.getCurrentTile());
          // stepcurrent.getCurrentTile().setWorker(currentWorker);
           stepcurrent.setTargetTile(currentWorker.getTargetTile(new Vector2d(0, 1)));

           if (currentWorker.getOwner().getCard().canMove(stepNumber, stepcurrent.isPowerActive())) {
               if (currentWorker.getOwner().getCard().isValidMove(stepcurrent, stepprevious, board)) {
                   currentWorker.getOwner().getCard().move(stepcurrent, stepprevious, board);
                   stepprevious = stepcurrent;
                   stepcurrent.setCurrentTile(currentWorker.getCurrentTile());
                   stepcurrent.setTargetTile(currentWorker.getTargetTile(new Vector2d(0, 1)));
                   stepNumber++;
               } else System.out.println("no move");
           }
           if (currentWorker.getOwner().getCard().canBuild(stepNumber, stepcurrent.isPowerActive())) {
               if (currentWorker.getOwner().getCard().isValidBuilding(stepcurrent, stepprevious, board)) {
                   currentWorker.getOwner().getCard().build(stepcurrent, stepprevious, board);
                   stepprevious = stepcurrent;
                   stepcurrent.setCurrentTile(currentWorker.getCurrentTile());
                   stepcurrent.setTargetTile(currentWorker.getTargetTile(new Vector2d(0, 1)));
                   stepNumber++;
               } else System.out.println("no build");
           }

           if (currentWorker.getOwner().getCard().checkWin(stepcurrent, stepprevious, board))
               System.out.println("hai vinto");
       }while (stepNumber<1);

   }




}
