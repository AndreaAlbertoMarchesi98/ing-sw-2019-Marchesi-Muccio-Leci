package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.VictoryMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ActionNames;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.model.Tile;

import java.util.ArrayList;

public class GameController {

    private ArrayList<VirtualView> views;
    private Game game;
    private Lobby lobby;
    private Server server;

    public GameController(Server server) {
        views = new ArrayList<>();
        game = Game.getInstance();
        this.server = server;
    }

    private void endGame(){
        for(VirtualView view : views){
            server.addWaitingView(view);
        }
    }

    public ArrayList<VirtualView> getViews() {
        return views;
    }

    public boolean isGameCreatable() {
        return views.isEmpty();
    }

    public boolean isLobbyJoinable() {

        if (lobby != null) {
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
        if (lobby.getPlayerCount() == lobby.getExpectedPlayersCount())
            notifyLobby();


        System.out.println("is lobby join"+(lobby.getPlayerCount() < lobby.getExpectedPlayersCount()));
    }

    public void createLobby(GameSetUpMessage message, VirtualView view) {
        System.out.println("creatingLobby");
        lobby = new Lobby(message.godNames);

        lobby.addPlayer(view.getPlayer());
        view.getPlayer().setPlayerNumber(lobby.getPlayerCount());
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
        VirtualView choosingView = views.get(choosingViewIndex);
        choosingView.updateLobby(message);
    }

    public void notifyBoard() {
        BoardMessage message = new BoardMessage(game);
        for (VirtualView view : views) {
            view.updateBoard(message);
        }
    }

    public void notifyVictory(int winnerNumber) {
        VictoryMessage message = new VictoryMessage(winnerNumber);
        for (VirtualView view : views) {
            view.updateVictory(message);
        }
    }

    //               view reactions
    public void placeWorkers(PlacementMessage message) {
        System.out.println("placing workers");
        Vector2d worker1Position = message.firstWorker;
        Vector2d worker2Position = message.secondWorker;
        Player player = game.getActivePlayer();
        game.getBoard().getTile(worker1Position).setWorker(new Worker(player));
        game.getBoard().getTile(worker2Position).setWorker(new Worker(player));

        game.nextTurn();

        if (game.getPlayerIndex() == 0)
            game.setAction(ActionNames.SELECT_WORKER);
        else
            game.setAction(ActionNames.PLACE_WORKERS);

        notifyBoard();
    }


    public void performAction(ActionMessage message, Player player) {
        System.out.println("performing action");
        Tile currentTile = game.getSelectedTile();
        Tile targetTile = game.getBoard().getTile(message.targetTile);
        System.out.println("selectedTile is: " + currentTile.getPosition().toString());
        boolean isPowerActive = game.isPowerActive();

        Step currentStep = new Step(currentTile, targetTile, isPowerActive);

        if (game.isPlayerTurn(player)) {
            Step previousStep = player.getPreviousStep();
            Card card = player.getCard();

            if (card.canMove(game.getStepNumber(), currentStep.isPowerActive()))
                card.move(currentStep, previousStep, game.getBoard());
            else if (card.canBuild(game.getStepNumber(), currentStep.isPowerActive()))
                card.build(currentStep, previousStep, game.getBoard());

            if (card.checkWin(currentStep, game.getBoard())) {
                notifyVictory(player.getPlayerNumber());

            }
            else {
                game.nextStep(targetTile);

                if (isTurnFinished(card, currentStep.isPowerActive())) {
                    game.nextTurn();
                    game.setAction(ActionNames.SELECT_WORKER);
                } else {
                    game.setSelectedTile(targetTile);
                    updateValidTiles();
                }
                notifyBoard();
            }
        }
    }

    private boolean isTurnFinished(Card card, boolean isPowerActive) {
        int stepNumber = game.getStepNumber();
        return !card.canMove(stepNumber, isPowerActive) && !card.canBuild(stepNumber, isPowerActive);
    }

    public void setPlayerCard(CardMessage message, VirtualView view) {
        GodName godName = message.godName;
        if (lobby.getAvailableCards().contains(godName)) {
            lobby.getAvailableCards().remove(godName);

            view.getPlayer().setCard(CardFactory.getCard(godName));
            view.getPlayer().setOriginalCard(CardFactory.getCard(godName));

            /*if (lobby.getAvailableCards().size() == 1) {
                Card lastCard = CardFactory.getCard(lobby.getAvailableCards().get(0));
                views.get(0).getPlayer().setCard(lastCard);
                startGame();
            }*/
            if (lobby.getAvailableCards().size() == 0) {
                startGame();
            }
            else
                notifyLobby();
        }

    }

    public void selectWorker(SelectionMessage message) {
        Tile selectedTile = game.getBoard().getTile(message.workerPosition);
        game.setSelectedTile(selectedTile);

        updateValidTiles();

        notifyBoard();
    }

    public void setPowerActive(PowerActiveMessage message) {
        game.setPowerActive(message.isPowerActive);

        if (game.getSelectedTile() != null) {

            updateValidTiles();
            notifyBoard();
        } else
            notifyBoard();
    }

    private void updateValidTiles() {
        System.out.println("calculating validTiles");
        boolean isPowerActive = game.isPowerActive();
        Tile selectedTile = game.getSelectedTile();
        Player player = selectedTile.getWorker().getOwner();
        Step currentStep = new Step(selectedTile, null, isPowerActive);

        if (player.getCard().canMove(game.getStepNumber(), isPowerActive)) {
            game.setAction(ActionNames.MOVE);
            game.setValidTiles(calculateValidMoves(currentStep, player));
        } else if (player.getCard().canBuild(game.getStepNumber(), isPowerActive)) {
            game.setAction(ActionNames.BUILD);
            game.setValidTiles(calculateValidBuilds(currentStep, player));
        } else {
            game.setAction(ActionNames.SELECT_WORKER);
            game.setValidTiles(new boolean[Board.size][Board.size]);
        }
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
