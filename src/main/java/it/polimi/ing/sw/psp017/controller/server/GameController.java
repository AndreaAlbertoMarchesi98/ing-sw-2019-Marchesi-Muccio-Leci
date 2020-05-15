package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.SDisconnectionMessage;
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

    public GameController(Server server, VirtualView firstView) {
        views = new ArrayList<>();
        this.server = server;

        System.out.println("start game creation");

        views.add(firstView);
        firstView.setGameController(this);
        firstView.getPlayer().setPlayerNumber(views.size());

        firstView.updateGameCreation();
    }

    public ArrayList<VirtualView> getViews() {
        return views;
    }

    public boolean isLobbyJoinable() {

        if (lobby != null) {
            return !lobby.isFull();
        } else
            return false;
    }

    public void addPlayerToLobby(VirtualView view) {
        System.out.println("addingPlayerToLobby");
        views.add(view);
        view.setGameController(this);

        view.getPlayer().setPlayerNumber(views.size());

        lobby.addPlayer(view.getPlayer());
        if (lobby.isFull())
            notifyLobby();
    }

    public void createLobby(GameSetUpMessage message, VirtualView view) {
        System.out.println("creatingLobby");
        lobby = new Lobby(message.godNames);

        lobby.addPlayer(view.getPlayer());
    }

    private void startGame() {
        game = new Game(lobby.getPlayers());
        notifyBoard();
    }
    private void endGame() {
        views.clear();
        lobby = null;
        server.removeGameController(this);
    }

    public void handleDisconnection(VirtualView view) {
        int disconnectedPlayerNumber = view.getPlayer().getPlayerNumber();
        views.remove(view);
        notifyDisconnection(disconnectedPlayerNumber);
        endGame();
    }

    private void setUpNextTurn(Step currentStep){
        addEffectOnOpponents(currentStep);


        game.getActivePlayer().resetCard();
        game.nextTurn();
        game.setAction(ActionNames.SELECT_WORKER);

        if(!hasPlayerMovesLeft()){
            System.out.println("\nplayer has no moves left\n");
            views.remove(views.get(game.getPlayerIndex()));
            game.removePlayer(game.getActivePlayer());
            game.nextTurn();

            if(game.getPlayers().size() == 1)
                notifyVictory(game.getPlayers().get(0).getPlayerNumber());
        }

    }
    private boolean hasPlayerMovesLeft(){
        for(Worker worker : game.getActivePlayer().getWorkers()){
            Step step = new Step(worker.getTile(), null, game.isPowerActive());
            boolean[][] validMoves = calculateValidMoves(step, game.getActivePlayer());
            for (int x = 0; x < Board.size; x++) {
                for (int y = 0; y < Board.size; y++) {
                    if(validMoves[x][y])
                        return true;
                }
            }
        }
        return false;
    }
    private void addEffectOnOpponents(Step currentStep){
        Player player = game.getActivePlayer();
        Card card = player.getCard();
        if(card.hasActiveDecorator(currentStep, player.getPreviousStep(), game.getBoard())) {
            System.out.println("applying decorator");
            for (Player opponent : game.getPlayers()) {
                if (!opponent.equals(player)) {
                    opponent.setCard(card.getDecorator(opponent.getCard()));
                }
            }
        }
    }

//              VIEW INTERACTION
//                 notifiers

    private void notifyLobby() {
        LobbyMessage message = new LobbyMessage(lobby);
        for (VirtualView view : views) {
            view.updateLobby(message);
        }
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

    private void notifyDisconnection(int disconnectedPlayerNumber){
        SDisconnectionMessage message = new SDisconnectionMessage(disconnectedPlayerNumber);
        for (VirtualView view : views) {
            view.updateDisconnection(message);
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
        System.out.println("performing action\n");
        Tile currentTile = game.getSelectedTile();
        Tile targetTile = game.getBoard().getTile(message.targetTile);
        boolean isPowerActive = game.isPowerActive();

        Step currentStep = new Step(currentTile, targetTile, isPowerActive);

        if (game.isPlayerTurn(player)) {
            Step previousStep = player.getPreviousStep();
            Card card = player.getCard();

            if (card.canMove(game.getStepNumber(), currentStep.isPowerActive()))
                card.move(currentStep, previousStep, game.getBoard());
            else if (card.canBuild(game.getStepNumber(), currentStep.isPowerActive()))
                card.build(currentStep, previousStep, game.getBoard());


            if (card.checkWin(currentStep, game.getBoard()))
                notifyVictory(player.getPlayerNumber());
            else {
                game.nextStep(targetTile);

                if(game.getStepNumber() == 2 && game.hasChoice())
                    game.setPowerActive(true);

                if (isTurnFinished(card)) {
                    setUpNextTurn(currentStep);
                } else {
                    game.setSelectedTile(targetTile);
                    updateValidTiles();
                }
                notifyBoard();
            }
        }
    }

    private boolean isTurnFinished(Card card) {
        int stepNumber = game.getStepNumber();
        boolean isPowerActive = game.isPowerActive();
        return !card.canMove(stepNumber, isPowerActive) && !card.canBuild(stepNumber, isPowerActive);
    }

    public void setPlayerCard(CardMessage message, VirtualView view) {
        GodName godName = message.godName;
        if (lobby.getAvailableCards().contains(godName)) {
            lobby.getAvailableCards().remove(godName);
            lobby.addChosenCard(godName);


            Card card = CardFactory.getCard(godName);
            view.getPlayer().setCard(card);
            view.getPlayer().setOriginalCard(card);


            if (lobby.getAvailableCards().size() == 0)
                startGame();
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
        Player player = game.getActivePlayer();

        if (isTurnFinished(player.getCard()))
            setUpNextTurn(player.getPreviousStep());
        else
            updateValidTiles();

        notifyBoard();
        /*
        if (game.getSelectedTile() != null) {
            updateValidTiles();
            notifyBoard();
        } else
            notifyBoard();

         */
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
