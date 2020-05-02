package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.GameCreationMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ActionNames;
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

    public ArrayList<VirtualView> getViews() {
        return views;
    }

    public boolean isGameCreatable() {
        return views.isEmpty();
    }

    public boolean isLobbyJoinable() {

        if (lobby != null) {
            System.out.println("joinable: " + (lobby.getPlayerCount() < lobby.getExpectedPlayersCount()));
            return lobby.getPlayerCount() < lobby.getExpectedPlayersCount();
        } else
            return false;
    }

    public void startGameCreation(VirtualView view) {
        System.out.println("start game creation");

        views.add(view);
        view.setGameController(this);
        view.getPlayer().setPlayerNumber(views.size());

        view.updateGameCreation();
    }

    public void addPlayerToLobby(VirtualView view) {
        System.out.println("addingPlayerToLobby");
        System.out.println(view.getPlayer().getPlayerNumber());
        views.add(view);
        view.setGameController(this);

        view.getPlayer().setPlayerNumber(views.size());

        lobby.addPlayer(view.getPlayer());
        if(lobby.getPlayerCount() == lobby.getExpectedPlayersCount())
            notifyLobby();
    }

    public void createLobby(GameSetUpMessage message, VirtualView view) {
        System.out.println("creatingLobby");
        lobby = new Lobby(message.godNames);

        lobby.addPlayer(view.getPlayer());
        view.getPlayer().setPlayerNumber(lobby.getPlayerCount());

        gameState = GameState.LOBBY;

    }

    private void startGame() {
        game.setUp(lobby.getPlayers());
        notifyBoard(null, ActionNames.PLACE_WORKERS);
    }

    public void handleDisconnection(VirtualView view) {

    }

//              VIEW INTERACTION
//                 notifiers

    private void notifyLobby() {
        LobbyMessage message = new LobbyMessage(lobby);
        int choosingViewIndex = lobby.getChoosingPlayerIndex();
        VirtualView choosingView = views.get(choosingViewIndex);
        choosingView.updateLobby(message);
    }


    public void notifyBoard(boolean[][] validTiles, ActionNames action) {
        System.out.println("activePlayerIndex is: " + game.getTurn().getPlayerIndex());
        BoardMessage message = new BoardMessage(game, validTiles, action);//da finire
        for (VirtualView view : views) {
            view.updateBoard(message);
        }
    }

    //               view reactions
    public void placeWorkers(PlacementMessage message) {
        System.out.println("placing workers");
        Vector2d worker1Position = message.firstWorker;
        Vector2d worker2Position = message.secondWorker;
        Player player = game.getTurn().getPlayer();
        System.out.println("assign player: "+player+" to worker");
        game.getBoard().getTile(worker1Position).setWorker(new Worker(player));
        game.getBoard().getTile(worker2Position).setWorker(new Worker(player));

        game.getTurn().nextTurn();
        if(game.getTurn().getPlayerIndex()==0)
            notifyBoard(null, ActionNames.SELECT_WORKER);
        else
            notifyBoard(null, ActionNames.PLACE_WORKERS);
    }

    public void performAction(ActionMessage message, Player player) {
        System.out.println("performing action");
        if (game.getTurn().getSelectedTile() != null) {
            Tile currentTile = game.getTurn().getSelectedTile();
            Tile targetTile = game.getBoard().getTile(message.targetTile);
            System.out.println("selectedTile is: " + currentTile.getPosition().toString());
            boolean isPowerActive = game.getTurn().isPowerActive();
            Step currentStep = new Step(currentTile, targetTile, isPowerActive);
            if (game.getTurn().isPlayerTurn(player)) {
                Step previousStep = player.getPreviousStep();
                Card card = player.getCard();

                if (card.canMove(game.getTurn().getStepNumber(), currentStep.isPowerActive()))
                    card.move(currentStep, previousStep, game.getBoard());
                else if (card.canBuild(game.getTurn().getStepNumber(), currentStep.isPowerActive()))
                    card.build(currentStep, previousStep, game.getBoard());

                game.getTurn().nextStep(targetTile);
                if(isTurnFinished(card,currentStep.isPowerActive()))//da sistemare
                {
                    game.getTurn().nextTurn();
                    notifyBoard(null, ActionNames.SELECT_WORKER);
                }
                else{
                    notifyBoard(calculateValidTiles(), calculateAction());
                }


            }
        }
    }
    private boolean isTurnFinished(Card card, boolean isPowerActive){
        int stepNumber = game.getTurn().getStepNumber();
        return !card.canMove(stepNumber, isPowerActive) && !card.canBuild(stepNumber, isPowerActive);
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

    public void selectWorker(SelectionMessage message){
        Tile selectedTile = game.getBoard().getTile(message.workerPosition);
        game.getTurn().setSelectedTile(selectedTile);

        notifyBoard(calculateValidTiles(), calculateAction());
    }

    public void setPowerActive(PowerActiveMessage message){
        game.getTurn().setPowerActive(message.isPowerActive);

        if(game.getTurn().getSelectedTile()!=null)
            notifyBoard(calculateValidTiles(),calculateAction());
        else
            notifyBoard(null,ActionNames.SELECT_WORKER);
    }

    private ActionNames calculateAction(){
        Card card = game.getTurn().getActivePlayer().getCard();
        int stepNumber = game.getTurn().getStepNumber();
        boolean isPowerActive = game.getTurn().isPowerActive();
        if (card.canMove(stepNumber, isPowerActive))
            return ActionNames.MOVE;
        else if (card.canBuild(stepNumber, isPowerActive))
            return ActionNames.BUILD;
        else
            return ActionNames.NONE;
    }

    private boolean[][] calculateValidTiles() {
        System.out.println("calculating validTiles");
        boolean isPowerActive = game.getTurn().isPowerActive();
        Tile selectedTile = game.getTurn().getSelectedTile();
        Player player = selectedTile.getWorker().getOwner();
        Step currentStep = new Step(selectedTile, null, isPowerActive);

        game.getTurn().setSelectedTile(selectedTile);
        game.getTurn().setPowerActive(isPowerActive);

        if (player.getCard().canMove(game.getTurn().getStepNumber(), isPowerActive))
            return calculateValidMoves(currentStep, player);
        else if (player.getCard().canBuild(game.getTurn().getStepNumber(), isPowerActive))
            return calculateValidBuilds(currentStep, player);
        else
            return new boolean[5][5];
    }

    private boolean[][] calculateValidMoves(Step currentStep, Player player) {
        Vector2d workerPosition = currentStep.getCurrentTile().getPosition();
        Card card = player.getCard();
        Step previousStep = player.getPreviousStep();
        boolean[][] validTiles = new boolean[5][5];
        for (int x = workerPosition.x - 1; x <= workerPosition.x + 1; x++) {
            for (int y = workerPosition.y - 1; y <= workerPosition.y + 1; y++) {
                Vector2d position = new Vector2d(x, y);
                if (Board.isInsideBounds(position) && !position.equals(workerPosition)) {
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
        boolean[][] validTiles = new boolean[5][5];
        for (int x = workerPosition.x - 1; x <= workerPosition.x + 1; x++) {
            for (int y = workerPosition.y - 1; y <= workerPosition.y + 1; y++) {
                Vector2d position = new Vector2d(x, y);
                if (Board.isInsideBounds(position) && !position.equals(workerPosition)) {
                    currentStep.setTargetTile(game.getBoard().getTile(position));
                    validTiles[x][y] = card.isValidBuilding(currentStep, previousStep, game.getBoard());
                }
            }
        }
        return validTiles;
    }


}
