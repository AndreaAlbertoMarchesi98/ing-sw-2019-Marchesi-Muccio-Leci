package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.GameCreationMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.ValidTilesMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.ValidTiles;
import it.polimi.ing.sw.psp017.model.Tile;

import java.util.ArrayList;

public class GameController {
    private enum GameState {
        GAME_OVER, LOBBY, GAME_RUNNING;
    }

    private GameState gameState;
    private ArrayList<VirtualView> views;
    private Game game;
    private Lobby lobby;

    public GameController() {
        views = new ArrayList<>();
        game = Game.getInstance();
    }

    public boolean isGameCreatable() {
        return views.isEmpty();
    }

    public boolean isLobbyJoinable() {

        if (lobby!=null) {
            System.out.println("joinable: "+(lobby.getPlayerCount() < lobby.getExpectedPlayersCount()));
            return lobby.getPlayerCount() < lobby.getExpectedPlayersCount();
        }
        else
            return false;
    }
    public void startGameCreation(VirtualView view) {
        System.out.println("start game creation");

        views.add(view);
        view.setGameController(this);
        view.getPlayer().setPlayerIndex(views.size());

        view.updateGameCreation();
    }
    public void addPlayerToLobby(VirtualView view) {
        System.out.println("addingPlayerToLobby");
        views.add(view);
        view.setGameController(this);
        view.getPlayer().setPlayerIndex(views.size());

        lobby.addPlayer(view.getPlayer());
        view.getPlayer().setPlayerIndex(lobby.getPlayerCount());

        notifyLobby();
    }
    public void createLobby(GameSetUpMessage message, VirtualView view) {
        System.out.println("creatingLobby");
        lobby = new Lobby(message.godNames);

        lobby.addPlayer(view.getPlayer());
        view.getPlayer().setPlayerIndex(lobby.getPlayerCount());

        gameState = GameState.LOBBY;

    }
    private void startGame() {
        game.setUp(lobby.getPlayers());
        notifyBoard();
    }

    public void handleDisconnection(VirtualView view) {

    }

//              VIEW INTERACTION
//                 notifiers

    private void notifyLobby() {
        LobbyMessage message = new LobbyMessage(lobby);
        int choosingViewIndex = lobby.getChoosingPlayerIndex();
        System.out.println("choosing player index: "+choosingViewIndex);
        VirtualView choosingView = views.get(choosingViewIndex);
        choosingView.updateLobby(message);
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
    public void placeWorkers(PlacementMessage message){
        Vector2d worker1Position = message.firstWorker;
        Vector2d worker2Position = message.secondWorker;
        Player player = game.getTurn().getPlayer();
        game.getBoard().getTile(worker1Position).setWorker(new Worker(player));
        game.getBoard().getTile(worker1Position).setWorker(new Worker(player));
    }
    public void performAction(ActionMessage message, Player player) {
        if(game.getTurn().getSelectedTile()!=null) {
            Tile currentTile = game.getTurn().getSelectedTile();
            Tile targetTile = game.getBoard().getTile(message.targetTile);
            boolean isPowerActive =game.getTurn().isPowerActive();
            Step currentStep = new Step(currentTile, targetTile, isPowerActive);
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
    }
    public void setPlayerCard(CardMessage message, VirtualView view) {
        GodName godName = message.godName;
        if (gameState == GameState.LOBBY && lobby.isPlayerTurn(view.getPlayer())) {
            if (lobby.getAvailableCards().contains(godName)) {
                lobby.getAvailableCards().remove(godName);

                view.getPlayer().setCard(CardFactory.getCard(godName));

                if (lobby.getAvailableCards().size() == 1) {
                    Card lastCard = CardFactory.getCard(lobby.getAvailableCards().get(0));
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
        Tile selectedTile = game.getBoard().getTile(workerPosition);
        Player player = selectedTile.getWorker().getOwner();
        Step currentStep = new Step(selectedTile,null,isPowerActive);

        game.getTurn().setSelectedTile(selectedTile);
        game.getTurn().setPowerActive(isPowerActive);

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
