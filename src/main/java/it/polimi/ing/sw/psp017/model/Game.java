package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;

/**
 * this is a Singleton game class
 */
public class Game {
    private ArrayList<Player> players;
    private boolean gameOver;
    private Player winner;
    private static Game single_instance = null;
    private Board board;
    private Turn turn;


    // private constructor restricted to this class itself

    public Board getBoard() {
        return board;
    }

    // static method to create instance of Singleton class
    public static Game getInstance() {
        if (single_instance == null) {
            System.out.println("new game created");
            single_instance = new Game();
        }
        return single_instance;
    }


    private Game() {
        board = new Board();
        players = new ArrayList<>();
        gameOver = false;
        winner = null;
    }


    public boolean isCreated() {
        if (single_instance != null)
            return true;
        else
            return false;
    }


    public Turn getTurn() {
        return turn;
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

    public boolean isGameOver() {
        return gameOver;
    }

    public Player getWinner() {
        return winner;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void setUp(ArrayList<Player> players) {
        this.players = players;
        turn = new Turn();
    }

    public class Turn{
        int number;
        int stepNumber;
        int playerIndex;
        Player player;
        boolean powerActive;
        Tile selectedTile;
        public Turn(){
            number = 0;
            stepNumber = 0;
            playerIndex = 0;
            player = players.get(playerIndex);
        }
        public void nextTurn(){
            number++;
            stepNumber = 0;
            if(playerIndex == players.size())
                playerIndex = 0;
            else
                playerIndex++;
            player = players.get(playerIndex);
            selectedTile = null;
        }
        public boolean hasChoice(){
            return player.getCard().hasChoice(stepNumber);
        }
        public Player getActivePlayer(){
            return  player;
        }
        public boolean isPlayerTurn(Player player) {
            return this.player.equals(player);
        }

        public int getPlayerIndex() {
            return playerIndex;
        }

        public Player getPlayer() {
            return player;
        }

        public int getStepNumber() {
            return stepNumber;
        }

        public Tile getSelectedTile() {
            return selectedTile;
        }

        public void setSelectedTile(Tile selectedTile) {
            this.selectedTile = selectedTile;
        }

        public boolean isPowerActive() {
            return powerActive;
        }

        public void setPowerActive(boolean powerActive) {
            this.powerActive = powerActive;
        }
    }

}
