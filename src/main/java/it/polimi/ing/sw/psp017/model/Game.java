package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private int turn;
    private boolean gameOver;
    private Player winner;
    private static Game single_instance = null;

    // private constructor restricted to this class itself

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
        Tile[][] createdTiles = new Tile[5][5];
        for (int x = 0; x < Board.size; x++) {
            for (int y = 0; y < Board.size; y++) {
                createdTiles[x][y]=new Tile(new Vector2d(x,y));
            }
        }
        Board.setTiles(createdTiles);
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

    public void addPlayer() {
    }

    public void startGame() {
    }


}
