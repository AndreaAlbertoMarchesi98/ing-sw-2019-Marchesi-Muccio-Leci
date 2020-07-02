package it.polimi.ing.sw.psp017.server.model;

import it.polimi.ing.sw.psp017.client.view.ActionNames;

import java.util.ArrayList;

public class Game {
    private ArrayList<Player> players;
    private Board board;
    private int stepNumber;
    private int playerIndex;
    private boolean powerActive;
    private Tile selectedWorkerTile;
    private boolean[][] validTiles;
    private ActionNames action;
    private Step savedStep;

    /**
     * create a new board, set a few game's paramenters
     *
     * @param players the game's players
     */
    public Game(ArrayList<Player> players) {
        board = new Board();
        this.players = players;
        stepNumber = 0;
        playerIndex = 0;
        action = ActionNames.PLACE_WORKERS;
    }

    /**
     * increase step number by 1
     */
    public void nextStep(){
        stepNumber++;
    }

    /**
     * set stepNumber to 0
     */
    public void restartTurn(){
        setAction(ActionNames.SELECT_WORKER);
        powerActive=false;
        selectedWorkerTile = null;
        stepNumber = 0;
        clearValidTiles();
    }

    /**
     * set parameters so that are a new turn begins, basically resets counters and
     * change playerIndex to next player's
     */
    public void nextTurn(){
        powerActive = false;
        selectedWorkerTile = null;
        stepNumber = 0;
        if(playerIndex >= players.size() - 1)
            playerIndex = 0;
        else
            playerIndex++;
    }

    /**
     * restore parameters so that game state returns to the beginning of the turn
     *
     * @param board the saved board that is restored
     */
    public void undo(Board board){
        this.board = board;
        stepNumber = 0;
        action = ActionNames.SELECT_WORKER;
        powerActive = false;
        selectedWorkerTile = null;
        getActivePlayer().setPreviousStep(savedStep);
    }

    /**
     * loop through board to return a deep copy of the board
     *
     * @return  a deep copy of the board
     */
    public Board getBoardCopy(){
        Board board = new Board();
        for (int x = 0; x < Board.size; x++) {
            for (int y = 0; y < Board.size; y++) {
                Tile newTile = board.getTile(new Vector2d(x,y));
                Tile currentTile = this.getBoard().getTile(new Vector2d(x,y));

                newTile.setLevel(currentTile.getLevel());
                if(currentTile.getWorker()!=null)
                    newTile.setWorker(new Worker(currentTile.getWorker().getOwner()));
                newTile.setDome(currentTile.isDome());
            }
        }
        savedStep = getActivePlayer().getPreviousStep();
        return board;
    }

    public Board getBoard() {
        return board;
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

    public void clearValidTiles() {
        this.validTiles = new boolean[Board.size][Board.size];
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

    public Tile getSelectedWorkerTile() {
        return selectedWorkerTile;
    }

    public void setSelectedWorkerTile(Tile selectedWorkerTile) {
        this.selectedWorkerTile = selectedWorkerTile;
    }

    public boolean isPowerActive() {
        return powerActive;
    }

    public void setPowerActive(boolean powerActive) {
        this.powerActive = powerActive;
    }
}
