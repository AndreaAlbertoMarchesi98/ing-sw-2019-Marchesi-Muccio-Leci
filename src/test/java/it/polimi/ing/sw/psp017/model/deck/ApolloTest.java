package it.polimi.ing.sw.psp017.model.deck;
import it.polimi.ing.sw.psp017.*;
import it.polimi.ing.sw.psp017.model.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class ApolloTest {
    private Board board;
    private GameTest game;
    private Player player1;
    private Player player2;
    private Worker worker1P1;
    private Worker worker2P1;
    private Worker worker1P2;


    @Before
    public void init(){
        game = new GameTest();

        player1 = new Player("PlayerApollo");
        player2 = new Player("Player2");
        game.addPlayer(player1);
        game.addPlayer(player2);
        board = game.getBoard();
        player1.setCard(new Apollo());
        player2.setCard(new BaseCard());

        worker1P1 = new Worker(player1);
        worker2P1 = new Worker(player1);
        worker1P2 = new Worker(player2);


        board.addWorker(worker1P1, new Vector2d(0,0));
        board.addWorker(worker1P2,new Vector2d(1,0));

    }



    @Test
    public void apolloMoveTest() {
        Card card = player1.getCard();
        Tile targetTile = worker1P2.getTile(board);
        Step currentstep = new Step(worker1P1.getTile(board), targetTile, false);

        assertTrue("error: valid move false. Target tile occupied by a enemy worker", card.isValidMove(currentstep, null, board));

        if (card.isValidMove(currentstep, null, board)) {
            Tile apolloTempTile = worker1P1.getTile(board);
            Tile enemyTemptile = worker1P2.getTile(board);

            card.move(currentstep, null, board);
            assertSame("error swap workers ",apolloTempTile, worker1P2.getTile(board));
            assertSame("error swap workers",enemyTemptile, worker1P1.getTile(board));
        }


    }

    @Test
    public void apolloIsValidMoveTest(){
        //casi base
        //mossa valida cella occupata da un suo worker
        //mossa valida cella occupata da un nemico
        //mossa valida cella occupata da un nemico su un livello sopra


        Tile targetTile = board.getTile(new Vector2d(0, 1));

        Step currentstep = new Step(worker1P1.getTile(board), targetTile, false);
        assertTrue("error: isValidMove false but tile free ", player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(true);
        assertFalse("error:isValidMove true but tile occupied  by dome",player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setDome(false);
        board.addWorker(worker2P1,targetTile.getPosition());
        assertFalse("error :isValidMove true but tile occupied  by worker of mine",player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setWorker(null);
        targetTile.setLevel(2);
        assertFalse("error: isValidMove true but level target tile is two step up", player1.getCard().isValidMove(currentstep, null, board));

        targetTile = worker1P2.getTile(board);
        currentstep = new Step( worker1P1.getTile(board),targetTile,false );

        assertTrue("error: isValidMove false but level target tile occupied by enemy worker", player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setLevel(1);
        assertTrue("error: isValidMove false but level target  tile occupied by enemy worker (level 1 up)", player1.getCard().isValidMove(currentstep, null, board));

        targetTile.setLevel(2);
        assertFalse("error: isValidMove true but level target  tile occupied by enemy worker (level 2 up)", player1.getCard().isValidMove(currentstep, null, board));

    }
}

