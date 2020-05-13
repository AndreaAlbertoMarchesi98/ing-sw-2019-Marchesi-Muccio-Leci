package it.polimi.ing.sw.psp017.model.deck;


import it.polimi.ing.sw.psp017.BaseCardTest;
import it.polimi.ing.sw.psp017.GameTest;
import it.polimi.ing.sw.psp017.model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PanTest  {
    private Board board;
    private GameTest game;
    private Player player1;
    private Worker worker1P1;

    @Before
    public void init(){
        game = new GameTest();

        player1 = new Player("PlayerPan");
        game.addPlayer(player1);
        board = game.getBoard();
        Card card = new Pan();
        player1.setCard(card);
        worker1P1 = new Worker(player1);

        board.addWorker(worker1P1, new Vector2d(0,0));


    }

    @Test
    public void panTest(){
        worker1P1.getTile(board).setLevel(2);
        Tile targetTile = board.getTile(new Vector2d(1,0));
         Step currentStep = new Step(worker1P1.getTile(board), targetTile, false);
        assertTrue( player1.getCard().checkWin(currentStep,board));
    }


}