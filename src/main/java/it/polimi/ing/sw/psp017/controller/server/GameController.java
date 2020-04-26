package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.ActionMessage;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.CardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.GameSetUpMessage;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.SelectionMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.ValidTilesMessage;
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

    public GameController() {
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
        if (lobby.getExpectedPlayersCount() == views.size())
            notifyLobby();
    }
    public void createLobby(GameSetUpMessage message, VirtualView view) {
        if (gameState == GameState.GAME_OVER) {
            lobby = new Lobby(view.getPlayer(), message.godNames);
            gameState = GameState.LOBBY;
        }
    }
    private void startGame() {
        game.setUp(lobby.getExpectedPlayersCount());
    }
    public void handleDisconnection(VirtualView view) {

    }

//              VIEW INTERACTION
//                 notifiers

    private void notifyLobby() {
        LobbyMessage message = new LobbyMessage(lobby);
        for (VirtualView view : views) {
            view.updateLobby(message);
        }
    }

    public void notifyValidTiles(boolean[][] validTiles) {
        ValidTilesMessage message= new ValidTilesMessage(validTiles);
        for (VirtualView view : views) {
            view.updateValidTiles(message);
        }
    }

    public void notifyBoard() {
        BoardMessage message= new BoardMessage(game.getBoard(),game.getTurn());
        for (VirtualView view : views) {
            view.updateBoard(message);
        }
    }
//               view reactions
    public void performAction(ActionMessage message, Player player) {
        Tile currentTile = game.getBoard().getTile(message.currentTile);
        Tile targetTile = game.getBoard().getTile(message.targetTile);
        Step currentStep = new Step(currentTile, targetTile, message.isPowerActive);
        if (game.getTurn().isPlayerTurn(player)) {
            Step previousStep = player.getPreviousStep();
            Card card = player.getCard();

            if (card.canMove(game.getTurn().getStepNumber(), currentStep.isPowerActive()))
                card.move(currentStep, previousStep, game.getBoard());
            else if (!card.canBuild(game.getTurn().getStepNumber(), currentStep.isPowerActive()))
                card.build(currentStep, previousStep, game.getBoard());

            notifyBoard();
        }
    }
    public void setPlayerCard(CardMessage message, VirtualView view) {
        GodName godName = message.godName;
        if (gameState == GameState.LOBBY && lobby.isPlayerTurn(view.getPlayer())) {
            if (lobby.getRemainingCards().contains(godName)) {
                lobby.getRemainingCards().remove(godName);

                view.getPlayer().setCard(CardFactory.getCard(godName));

                if (lobby.getRemainingCards().size() == 1) {
                    Card lastCard = CardFactory.getCard(lobby.getRemainingCards().get(0));
                    views.get(0).getPlayer().setCard(lastCard);
                    startGame();
                } else
                    notifyLobby();
            }
        }
    }
    public void calculateValidTiles(SelectionMessage selectionMessage) {
        boolean isPowerActive = selectionMessage.isPowerActive;
        Vector2d workerPosition = selectionMessage.workerPosition;
        Tile currentTile = game.getBoard().getTile(workerPosition);
        Player player = currentTile.getWorker().getOwner();
        Step currentStep = new Step(currentTile,null,isPowerActive);

        if(player.getCard().canMove(game.getTurn().getStepNumber(), isPowerActive))
            notifyValidTiles(calculateValidMoves(currentStep, player));
        else if(player.getCard().canBuild(game.getTurn().getStepNumber(), isPowerActive))
            notifyValidTiles(calculateValidBuilds(currentStep, player));
        else
            notifyValidTiles(new boolean[3][3]);
    }
    private boolean[][] calculateValidMoves(Step currentStep, Player player) {
        Vector2d workerPosition = currentStep.getCurrentTile().getPosition();
        Card card = player.getCard();
        Step previousStep = player.getPreviousStep();
        boolean [][] validTiles = new boolean[3][3];
        for (int x = workerPosition.x - 1; x < ValidTiles.size; x++) {
            for (int y = workerPosition.y - 1; y < ValidTiles.size; y++) {
                if (x != workerPosition.x && y != workerPosition.y) {
                    Vector2d position = new Vector2d(x,y);
                    currentStep.setTargetTile(game.getBoard().getTile(position));
                    validTiles[x][y] = card.isValidMove(currentStep, previousStep, game.getBoard());
                }
            }
        }
        return validTiles;
    }
    private boolean[][] calculateValidBuilds(Step currentStep, Player player) {
        Vector2d workerPosition = currentStep.getCurrentTile().getPosition();
        Card card = player.getCard();
        Step previousStep = player.getPreviousStep();
        boolean [][] validTiles = new boolean[3][3];
        for (int x = workerPosition.x - 1; x < ValidTiles.size; x++) {
            for (int y = workerPosition.y - 1; y < ValidTiles.size; y++) {
                if (x != workerPosition.x && y != workerPosition.y) {
                    Vector2d position = new Vector2d(x,y);
                    currentStep.setTargetTile(game.getBoard().getTile(position));
                    validTiles[x][y] = card.isValidBuilding(currentStep, previousStep, game.getBoard());
                }
            }
        }
        return validTiles;
    }


}
