package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;
public class Game {
    public static final int SIZE_BOARD = 5;
    private ArrayList<Player> players;
    private int turn;
    private boolean gameOver;
    private Player winner;
    private static Tile [][] board = new Tile[SIZE_BOARD][SIZE_BOARD];

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

    public static void setTile(BoardPosition boardPosition, Tile tile) {
        Game.board[boardPosition.x][boardPosition.y] = tile;
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

    public static Tile[][] getBoard() {
        return board;
    }

    public Player getWinner() {
        return winner;
    }
    public void addPlayer (){}

    public void startGame () {}

}
