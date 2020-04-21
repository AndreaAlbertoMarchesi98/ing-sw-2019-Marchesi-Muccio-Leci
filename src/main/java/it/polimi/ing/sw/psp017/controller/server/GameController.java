package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.ValidTiles;

import java.util.ArrayList;

public class GameController {
    private enum GameState {
        GAME_OVER, LOBBY, GAME_RUNNING
    }

    private GameState gameState;
    private boolean gameRunning;
    private ArrayList<VirtualView> views;
    private Game game;
    private Lobby lobby;

    public GameController(){
        game = Game.getInstance();
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public boolean isLobbyFull() {
        if (gameState == GameState.LOBBY)
            return lobby.getPlayerCount() < lobby.getExpectedPlayersCount();
        else
            return false;
    }

    public void startGameCreation(VirtualView view) {
        views.add(view);
        gameRunning = true;

    }

    public void addPlayerToLobby(VirtualView view) {
        views.add(view);
        lobby.addPlayer(view.getPlayer());
        if(lobby.getExpectedPlayersCount()==views.size())
            notifyLobbyCompleted(lobby.getPlayersNames(),lobby.getRemainingCards());
    }

    public void createLobby(VirtualView view, ArrayList<GodName> godNames) {
        if (gameState == GameState.GAME_OVER) {
            lobby = new Lobby(view.getPlayer(), godNames);
            gameState = GameState.LOBBY;
        }
    }

    public void setPlayerCard(GodName godName, VirtualView view) {
        if (gameState == GameState.LOBBY && lobby.isPlayerTurn(view.getPlayer())) {
            if(lobby.getRemainingCards().contains(godName)) {
                lobby.getRemainingCards().remove(godName);

                view.getPlayer().setCard(CardFactory.getCard(godName));

                if(lobby.getRemainingCards().size()==1) {
                    Card lastCard = CardFactory.getCard(lobby.getRemainingCards().get(0));
                    views.get(0).getPlayer().setCard(lastCard);
                    startGame();
                }
                else
                    notifyRemainingCards(lobby.getRemainingCards());
            }
        }
    }

    public void calculateValidTiles(Vector2d workerPosition, boolean isPowerActive, VirtualView view){
        Worker worker = game.getBoard().getTile(workerPosition).getWorker();
        boolean[][] validTiles = new boolean[5][5];
        //validTiles = calculate valid tiles
        notifyValidTiles(validTiles);
    }

    public void performAction(Step currentStep, Player player){
        if(game.getTurn().isPlayerTurn(player)) {
            Step previousStep = player.getPreviousStep();
            Card card = player.getCard();

            if (card.canMove(game.getTurn().getStep(), currentStep.isPowerActive())) {
                card.move(currentStep, previousStep, game.getBoard());
                notifyBoard(game.getBoard());
            }
            else if (!card.canBuild(game.getTurn().getStep(), currentStep.isPowerActive())) {
                card.build(currentStep, previousStep, game.getBoard());
                notifyBoard(game.getBoard());
            }
        }
    }

    private void startGame(){
        game.setUp(lobby.getExpectedPlayersCount());
    }

    private void notifyLobbyCompleted(ArrayList<String> players, ArrayList<GodName> cards){
            for (VirtualView view : views) {
                view.updateLobby(players, cards);
            }
    }
    private void notifyRemainingCards(ArrayList<GodName> cards){
        for (VirtualView view : views) {
            view.updateCardChoices(cards);
        }
    }
    public void notifyValidTiles(boolean[][] validTiles){
        for (VirtualView view : views) {
            view.updateValidTiles(validTiles);
        }
    }
    public void notifyBoard(Board board){
        for (VirtualView view : views) {
            view.updateBoard(board);
        }
    }

    public void handleDisconnection(VirtualView view){

    }


    private ValidTiles calculateValidMoves(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Vector2d workerPosition = currentTile.getPosition();
        Card card = currentTile.getWorker().getOwner().getCard();
        ValidTiles validTiles = new ValidTiles(true, currentTile.getPosition());
        for (int x = 0; x < ValidTiles.size; x++) {
            for (int y = 0; y < ValidTiles.size; y++) {
                if (x != 1 && y != 1) {
                    Vector2d leftBottomPosition = Vector2d.subtractVectors(workerPosition, new Vector2d(1, 1));
                    Vector2d position = Vector2d.sumVectors(leftBottomPosition, new Vector2d(x, y));

                    currentStep.setTargetTile(board.getTile(position));
                    boolean isValidMove = card.isValidMove(currentStep, previousStep, board);
                    validTiles.setTile(isValidMove, new Vector2d(x, y));
                }
            }
        }
        return validTiles;
    }

    private ValidTiles calculateValidBuilds(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Vector2d workerPosition = currentTile.getPosition();
        Card card = currentTile.getWorker().getOwner().getCard();
        ValidTiles validTiles = new ValidTiles(false, currentTile.getPosition());
        for (int x = 0; x < ValidTiles.size; x++) {
            for (int y = 0; y < ValidTiles.size; y++) {
                if (x != 1 && y != 1) {
                    Vector2d leftBottomPosition = Vector2d.subtractVectors(workerPosition, new Vector2d(1, 1));
                    Vector2d position = Vector2d.sumVectors(leftBottomPosition, new Vector2d(x, y));

                    currentStep.setTargetTile(board.getTile(position));
                    boolean isValidMove = card.isValidBuilding(currentStep, previousStep, board);
                    validTiles.setTile(isValidMove, new Vector2d(x, y));
                }
            }
        }
        return validTiles;
    }



}
