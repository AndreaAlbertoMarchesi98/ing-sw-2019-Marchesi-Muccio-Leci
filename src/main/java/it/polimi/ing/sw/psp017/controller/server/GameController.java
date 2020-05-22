package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.ServerDisconnectionMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.VictoryMessage;
import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ActionNames;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.model.Tile;

import java.util.ArrayList;

public class GameController {

    private ArrayList<VirtualView> views;
    private Game game;
    private Board savedBoard;
    private Lobby lobby;
    private final Server server;
    private boolean isUndoPossible;
    private boolean hasUndoArrived;

    private enum GameState {
        WORKER_PLACEMENT,
    }

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

    public synchronized void addViewToLobby(VirtualView view) {
        System.out.println("addingPlayerToLobby");
        views.add(view);
        view.setGameController(this);

        view.getPlayer().setPlayerNumber(views.size());

        lobby.addPlayer(view.getPlayer());
        if (lobby.isFull())
            notifyLobby();
    }

    public synchronized void createLobby(GameSetUpMessage message, VirtualView view) {
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

    public synchronized void handleDisconnection(VirtualView view) {
        int disconnectedPlayerNumber = view.getPlayer().getPlayerNumber();
        views.remove(view);
        notifyDisconnection(disconnectedPlayerNumber);
        endGame();
    }

    private void setUpNextTurn(Step currentStep) {
        System.out.println("next turn\n");

        addEffectOnOpponents(currentStep);

        game.getActivePlayer().resetCard();
        game.nextTurn();
        game.setAction(ActionNames.SELECT_WORKER);

        if (!hasPlayerMovesLeft()) {
            System.out.println("\nplayer has no moves left\n");
            views.remove(views.get(game.getPlayerIndex()));
            game.removePlayer(game.getActivePlayer());
            game.nextTurn();

            if (game.getPlayers().size() == 1)
                notifyVictory(game.getPlayers().get(0).getPlayerNumber());
        }

        game.setValidTiles(new boolean[5][5]);
        savedBoard = game.getBoardCopy();
    }

    private boolean hasPlayerMovesLeft() {
        for (Worker worker : game.getActivePlayer().getWorkers()) {
            Step step = new Step(worker.getTile(), null, game.isPowerActive());
            boolean[][] validMoves = calculateValidMoves(step, game.getActivePlayer());
            for (int x = 0; x < Board.size; x++) {
                for (int y = 0; y < Board.size; y++) {
                    if (validMoves[x][y])
                        return true;
                }
            }
        }
        return false;
    }

    private void addEffectOnOpponents(Step currentStep) {
        Player player = game.getActivePlayer();
        Card card = player.getCard();
        if (card.hasActiveDecorator(currentStep, player.getPreviousStep(), game.getBoard())) {
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

    private void notifyDisconnection(int disconnectedPlayerNumber) {
        ServerDisconnectionMessage message = new ServerDisconnectionMessage(disconnectedPlayerNumber);
        for (VirtualView view : views) {
            view.updateDisconnection(message);
        }
    }
    //               view reactions

    public synchronized void processSelection(SelectedTileMessage message, Player player) {
        synchronized (this) {
            if (game.isPlayerTurn(player)) {
                if (Board.isInsideBounds(message.tilePosition)) {
                    Tile selectedTile = game.getBoard().getTile(message.tilePosition);

                    if (game.getAction() == ActionNames.PLACE_WORKERS)
                        placeWorker(selectedTile, player);
                    else if (game.getAction() == ActionNames.SELECT_WORKER)
                        selectWorker(selectedTile, player);
                    else if (game.getAction() == ActionNames.MOVE || game.getAction() == ActionNames.BUILD)
                        performAction(selectedTile, player);
                }
            }
        }
    }

    private void placeWorker(Tile selectedTile, Player player) {
        if (selectedTile.getWorker() == null) {
            System.out.println("player: " + player.getPlayerNumber() + " is placing a worker");
            Worker worker = new Worker(player);
            selectedTile.setWorker(worker);
            player.addWorker(worker);
            if (player.getWorkers().size() == 2) {
                game.nextTurn();

                if (game.getPlayerIndex() == 0)
                    game.setAction(ActionNames.SELECT_WORKER);
                else
                    game.setAction(ActionNames.PLACE_WORKERS);

            }
        } else
            game.setValidTiles(new boolean[Board.size][Board.size]);
        notifyBoard();
    }

    private void selectWorker(Tile selectedTile, Player player) {
        System.out.println("player: " + player.getPlayerNumber() + " is selecting a worker");

        if (selectedTile.getWorker() != null && player.equals(selectedTile.getWorker().getOwner())) {
            game.setSelectedTile(selectedTile);

            updateValidTiles();

        } else {
            game.setAction(ActionNames.SELECT_WORKER);

            game.setValidTiles(new boolean[Board.size][Board.size]);
        }
        notifyBoard();
    }


    private void performAction(Tile targetTile, Player player) {
        if (game.getValidTiles()[targetTile.getPosition().x][targetTile.getPosition().y]) {
            hasUndoArrived = false;
            isUndoPossible = true;
            long finishTime = System.nanoTime() + 5000;
            while (System.nanoTime() < finishTime) {
                if (hasUndoArrived) {
                    isUndoPossible = false;
                    game.restore(savedBoard);
                    return;
                }
            }
            isUndoPossible = false;


            System.out.println("build/move with worker");
            Tile currentTile = game.getSelectedTile();
            boolean isPowerActive = game.isPowerActive();
            System.out.println("power active is: " + isPowerActive);

            Step currentStep = new Step(currentTile, targetTile, isPowerActive);

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

                if (game.getStepNumber() == 2 && game.hasChoice())
                    game.setPowerActive(true);

                if (isTurnFinished(card)) {
                    setUpNextTurn(currentStep);
                } else {
                    game.setSelectedTile(targetTile);
                    updateValidTiles();
                }
                player.setPreviousStep(currentStep);

                notifyBoard();
            }
        }

    }

    private boolean isTurnFinished(Card card) {
        int stepNumber = game.getStepNumber();
        boolean isPowerActive = game.isPowerActive();
        return !card.canMove(stepNumber, isPowerActive) && !card.canBuild(stepNumber, isPowerActive);
    }

    public synchronized void setPlayerCard(CardMessage message, VirtualView view) {
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

    public synchronized void setPowerActive() {
        System.out.println("before pressed button power" + game.isPowerActive());
        game.setPowerActive(!game.isPowerActive());
        System.out.println("after pressed button power" + game.isPowerActive());
        Player player = game.getActivePlayer();

        if (isTurnFinished(player.getCard()))
            setUpNextTurn(player.getPreviousStep());
        else {
            updateValidTiles();
            updateActions(player);
        }

        notifyBoard();
    }

    private void updateActions(Player player) {
        boolean isPowerActive = game.isPowerActive();
        if (player.getCard().canMove(game.getStepNumber(), isPowerActive))
            game.setAction(ActionNames.MOVE);
        else if (player.getCard().canBuild(game.getStepNumber(), isPowerActive))
            game.setAction(ActionNames.BUILD);
        else
            game.setAction(ActionNames.SELECT_WORKER);
    }

    private void updateValidTiles() {
        System.out.println("calculating validTiles");
        boolean isPowerActive = game.isPowerActive();
        Tile workerTile = game.getSelectedTile();
        if (workerTile.getWorker() != null) {
            Player player = workerTile.getWorker().getOwner();
            Step currentStep = new Step(workerTile, null, isPowerActive);

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

    public synchronized void undo() {
        if(isUndoPossible)
            hasUndoArrived = true;
    }
}
