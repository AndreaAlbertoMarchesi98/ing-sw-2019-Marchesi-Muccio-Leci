package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.server.model.*;
import it.polimi.ing.sw.psp017.client.view.ActionNames;

import java.util.ArrayList;

/**
 * this is a Game version not singleton for tests
 */
public class GameTest {
    private ArrayList<Player> players;
    private Board board;
    private int stepNumber;
    private int playerIndex;
    private boolean powerActive;
    private Tile selectedTile;
    private boolean[][] validTiles;
    private ActionNames action;



    public Board getBoard() {
        return board;
    }



     public GameTest() {
        board = new Board();
        players = new ArrayList<>();
    }

    public void setUp(ArrayList<Player> players) {
        this.players = players;
        stepNumber = 0;
        playerIndex = 0;
        action = ActionNames.PLACE_WORKERS;
    }



    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }



    public void nextStep(Tile targetTile){
        stepNumber++;
        selectedTile = targetTile;
    }
    public void nextTurn(){
        powerActive = false;
        stepNumber = 0;
        if(playerIndex == players.size() - 1)
            playerIndex = 0;
        else
            playerIndex++;
        selectedTile = null;
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
