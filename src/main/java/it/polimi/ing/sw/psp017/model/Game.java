package it.polimi.ing.sw.psp017.model;

import it.polimi.ing.sw.psp017.view.ActionNames;

import java.util.ArrayList;

/**
 * this is a Singleton game class
 */
public class Game {
    private ArrayList<Player> players;
    private static Game single_instance = null;
    private Board board;
    private int stepNumber;
    private int playerIndex;
    private boolean powerActive;
    private Tile selectedTile;
    private boolean[][] validTiles;
    private ActionNames action;


    // private constructor restricted to this class itself

    public Board getBoard() {
        return board;
    }

    public Game(ArrayList<Player> players) {
        board = new Board();
        this.players = players;
        stepNumber = 0;
        playerIndex = 0;
        action = ActionNames.PLACE_WORKERS;
    }


    public boolean isCreated() {
        if (single_instance != null)
            return true;
        else
            return false;
    }


    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public void removePlayer(Player p) {
        players.remove(p);


    }



    public void nextStep(Tile targetTile){
        stepNumber++;
        selectedTile = targetTile;
    }
    public void nextTurn(){
        powerActive = false;
        stepNumber = 0;
        if(playerIndex >= players.size() - 1)
            playerIndex = 0;
        else
            playerIndex++;
        selectedTile = null;
    }

    public void undo(Board board){
        this.board = board;
        stepNumber = 0;
        powerActive = false;
        selectedTile = null;
    }
    public Board getBoardCopy(){
        Board board = new Board();
        for (int x = 0; x < Board.size; x++) {
            for (int y = 0; y < Board.size; y++) {
                Tile newTile = board.getTile(new Vector2d(x,y));
                Tile currentTile = this.getBoard().getTile(new Vector2d(x,y));

                newTile.setLevel(currentTile.getLevel());
                newTile.setWorker(currentTile.getWorker());
                newTile.setDome(currentTile.isDome());
            }
        }
        return board;
    }

    public ActionNames getAction() {
        return action;
    }

    public void setAction(ActionNames action) {
        this.action = action;
    }

    public boolean[][] getValidTiles() {
        return validTiles;
    }

    public void setValidTiles(boolean[][] validTiles) {
        this.validTiles = validTiles;
    }

    public boolean hasChoice(){
            return players.get(playerIndex).getCard().hasChoice(stepNumber);
        }

    public Player getActivePlayer(){
        return  players.get(playerIndex);
    }

    public boolean isPlayerTurn(Player player) {
        return players.get(playerIndex).equals(player);
    }

    public int getPlayerIndex() {
        return playerIndex;
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
