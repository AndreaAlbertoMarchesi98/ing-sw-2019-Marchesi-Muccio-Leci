package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;

public class Game {
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
    public static Game getInstance()
    {
        if (single_instance == null) {
            System.out.println("new game create");
            single_instance = new Game();
        }
        return single_instance;
    }


    private Game() {
        board = new Board();
        players = new ArrayList<Player>();
        turn = 0;
        gameOver = false;
        winner = null;
}




    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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

    public void startGame() {
    }


}
