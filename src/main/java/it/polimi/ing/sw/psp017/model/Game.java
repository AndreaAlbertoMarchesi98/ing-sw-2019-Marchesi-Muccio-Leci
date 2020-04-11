package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;

/**
 * this is a Singleton game class
 */
public class Game {
    private int playersCount;
    private ArrayList<Player> players;
    private int turn;
    private boolean gameOver;
    private Player winner;
    private static Game single_instance = null;
    private Board board;

    // private constructor restricted to this class itself

    public Board getBoard() {
        return board;
    }
    // static method to create instance of Singleton class
    public static Game getInstance(int playersCount)
    {
        if (single_instance == null) {
            System.out.println("new game created");
            single_instance = new Game(playersCount);
        }
        return single_instance;
    }


    private Game(int playersCount) {
        board = new Board();
        players = new ArrayList<>();
        turn = 0;
        gameOver = false;
        winner = null;
        this.playersCount = playersCount;
}


    public boolean isCreated() {
        if(single_instance!=null)
            return true;
        else
            return false;
    }

    public int getPlayersCount() {
        return playersCount;
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

    public ArrayList<Player> getPlayers() {
        return players;
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
