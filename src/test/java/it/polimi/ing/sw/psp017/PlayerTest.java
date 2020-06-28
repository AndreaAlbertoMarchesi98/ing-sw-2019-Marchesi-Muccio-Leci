package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.server.model.*;
import it.polimi.ing.sw.psp017.server.model.deck.BaseCard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;

public class PlayerTest {
    private Board board;
    private GameTest game;
    private Player player1;
    private Player player2;
    private Worker worker1P1;
    private Worker worker2P1;
    private Worker worker1P2;
    private Worker worker2P2;

    @Before
    public void init(){

        game = new GameTest();

        player1 = new Player("Player1");
        player2 = new Player("Player2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        board = game.getBoard();
        Card card = new BaseCard();
        player1.setCard(card);
        player2.setCard(card);

        worker1P1 = new Worker(player1);
        worker2P1 = new Worker(player1);
        worker1P2 = new Worker(player2);
        worker2P2 = new Worker(player2);


        board.addWorker(worker1P1, new Vector2d(0,0));
        board.addWorker(worker2P1,new Vector2d(1,0));

    }





    @Test
    public void testPlayer()
    {


        //player1.getPreviousStep().setCurrentTile(null);
        System.out.println(player1.getNickname()+ player1.getPlayerNumber());


           Step temp = new Step(new Tile(new Vector2d(4,4)),new Tile(new Vector2d(4,3)),false);
           player1.setPreviousStep(temp);
           temp.setCurrentTile(new Tile(new Vector2d(4,4)));
           assertNotEquals(player1.getPreviousStep().getCurrentTile(),temp.getCurrentTile());


        player1.reset();
        System.out.println(player1.getNickname());

        player1.setPlayerNumber(2);
        assertEquals(player1.getPlayerNumber(),2);

        player1.reset();
        player1.reset();
        assertNotNull(player1.getWorkers());
        player1.resetCard();
        assertNull(player1.getCard());
        player1.setOriginalCard(new BaseCard());
        player1.reset();


        player1.addWorker(worker2P2);
        assertEquals(player1.getWorkers().get(0),worker2P2);


    }
}
