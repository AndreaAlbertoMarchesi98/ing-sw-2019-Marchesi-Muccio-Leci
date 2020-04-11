package it.polimi.ing.sw.psp017;
import it.polimi.ing.sw.psp017.model.*;

import java.util.ArrayList;

//without singleton
public class GameTest {
    private ArrayList<Player> players;
    private int turn;
    private boolean gameOver;
    private Player winner;
    private Board board;

    // private constructor restricted to this class itself

    public Board getBoard() {
        return board;
    }
    // static method to create instance of Singleton class



    public GameTest() {
        board = new Board();
        players = new ArrayList<Player>();
        turn = 0;
        gameOver = false;
        winner = null;
    }


    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }



}
