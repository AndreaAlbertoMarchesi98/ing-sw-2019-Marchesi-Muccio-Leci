package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
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
    private volatile boolean isUndoPossible;
    private volatile boolean hasUndoArrived;
    private volatile boolean hasSkippedUndo;

    private enum GameSate {
        SET_UP, LOBBY, WORKERS_PLACEMENT, MATCH, GAME_OVER
    }

    private GameSate gameState;

    /**
     * assign server, initialize views and calls startGameSetUp
     *
     * @param server    server's reference
     * @param firstView first view of the game
     */
    public GameController(Server server, VirtualView firstView) {
        views = new ArrayList<>();
        this.server = server;
        startGameCreation(firstView);
    }
    //VIEWS OPERATIONS

    /**
     * add view to views, set game controller, set playerNumber
     *
     * @param view view to add
     */
    private void addView(VirtualView view) {
        views.add(view);
        view.setGameController(this);
        view.getPlayer().setPlayerNumber(views.size());
    }

    /**
     * remove view from views, remove player from game
     *
     * @param view view to add
     */
    private void removeView(VirtualView view) {
        views.remove(view);
        if (game != null)
            game.removePlayer(view.getPlayer());
    }

    /**
     * @return the views
     */
    public ArrayList<VirtualView> getViews() {
        return views;
    }

    /**
     * @return true if the lobby is joinable else false
     */
    public boolean isLobbyJoinable() {
        if (lobby != null) {
            return !lobby.isFull();
        } else
            return false;
    }

    /**
     * add view to views, add player to lobby
     *
     * @param view view to add
     */
    public synchronized void addViewToLobby(VirtualView view) {
        System.out.println("addingPlayerToLobby");
        addView(view);
        lobby.addPlayer(view.getPlayer());
        if (lobby.isFull())
            notifyLobby();
    }
    //GAME STATE OPERATIONS

    /**
     * add view, set gameState to SET_UP state, start gameCreation, tell server that this controller
     * is waiting for new players
     *
     * @param firstView view to add and to start set up
     */
    private void startGameCreation(VirtualView firstView) {
        System.out.println("start game creation");
        addView(firstView);
        gameState = GameSate.SET_UP;
        firstView.updateGameCreation();
    }

    /**
     * change gameState to LOBBY state, creates lobby, adds the first view to it
     *
     * @param message contains the god names to create the lobby
     * @param view    first view to add to lobby
     */
    public synchronized void createLobby(GameSetUpMessage message, VirtualView view) {
        System.out.println("creatingLobby");
        gameState = GameSate.LOBBY;
        lobby = new Lobby(message.godNames);
        lobby.addPlayer(view.getPlayer());
        server.addWaitingGameController(this);
    }

    /**
     * change gameState to WORKERS_PLACEMENT state, start game, notify players of the board
     */
    private void startGame() {
        System.out.println("starting game");
        gameState = GameSate.WORKERS_PLACEMENT;
        game = new Game(lobby.getPlayers());
        notifyBoard();
    }

    /**
     * close completely the game and all its references, stop view threads
     */
    private void endGame() {
        System.out.println("ending game");
        for (VirtualView view : views)
            view.stop();
        server.removeGameController(this);
    }
//              VIEW INTERACTION
//                 NOTIFIERS

    /**
     * notify the lobby to every view
     */
    private void notifyLobby() {
        LobbyMessage message = new LobbyMessage(lobby);
        for (VirtualView view : views) {
            view.updateLobby(message);
        }
    }

    /**
     * notify the board to every view
     */
    public void notifyBoard() {
        BoardMessage message = new BoardMessage(game);
        for (VirtualView view : views) {
            view.updateBoard(message);
        }
    }

    /**
     * notify defeatedView that it has no more moves
     *
     * @param defeatedView view to notify
     */
    public void notifyDefeat(VirtualView defeatedView) {
        defeatedView.updateDefeat(new NoMovesMessage());
    }

    /**
     * notify every view that a player has won
     *
     * @param winnerNumber number of the player who has won
     */
    public void notifyVictory(int winnerNumber) {
        System.out.println("notify victory");
        VictoryMessage message = new VictoryMessage(winnerNumber);
        for (VirtualView view : views) {
            view.updateVictory(message);
        }
    }

    /**
     * notify every view that a view disconnected
     *
     * @param disconnectedView view that disconnected
     */
    private void notifyDisconnection(VirtualView disconnectedView) {
        int disconnectedPlayerNumber = disconnectedView.getPlayer().getPlayerNumber();
        ServerDisconnectionMessage message = new ServerDisconnectionMessage(disconnectedPlayerNumber);
        for (VirtualView view : views)
            view.updateDisconnection(message);
    }
    //              VIEW INTERACTION
    //                 UPDATERS

    /**
     * notify every view that a view disconnected
     *
     * @param message view that disconnected
     * @param view    view that disconnected
     */
    public synchronized void setPlayerCard(CardMessage message, VirtualView view) {
        if (gameState == GameSate.LOBBY) {
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
    }

    /**
     * set activePlayer's powerActive, pass turn if it's finished else update validTiles, then notify board
     */
    public synchronized void setPowerActive() {
        if (gameState == GameSate.MATCH) {
            if (game.hasChoice()) {
                System.out.println("setting power Active to: " + !game.isPowerActive());
                game.setPowerActive(!game.isPowerActive());
                if (isTurnFinished())
                    setUpNextTurn(game.getActivePlayer().getPreviousStep(), game.getActivePlayer().getPreviousStep());
                else
                    updateValidTiles();
                notifyBoard();
            }
        }
    }

    /**
     * save that undo has arrived if undo is possible
     */
    public void receiveUndo() {
        if (gameState == GameSate.MATCH) {
            if (isUndoPossible) {
                System.out.println("undo has arrived");
                hasUndoArrived = true;
            }
        }
    }

    /**
     * skip undo if the performMove method was waiting for it, control that selection is valid,
     * then depending on what type of selection it is, either call selectWorker, performAction, or placeWorker
     *
     * @param message contains target tile position
     * @param player  is the player that is selecting
     */
    public void processSelection(SelectedTileMessage message, Player player) {
        Vector2d targetPosition = message.tilePosition;
        Tile selectedTile = game.getBoard().getTile(targetPosition);
        if (Board.isInsideBounds(targetPosition)) {
            if (game.isPlayerTurn(player)) {
                if (gameState == GameSate.MATCH) {
                    if (game.getAction() == ActionNames.MOVE || game.getAction() == ActionNames.BUILD) {
                        if (isUndoPossible)
                            hasSkippedUndo = true;
                    }
                }
            }
            synchronized (this) {
                if (game.isPlayerTurn(player)) {
                    if (gameState == GameSate.MATCH) {
                        if (game.getAction() == ActionNames.SELECT_WORKER)
                            selectWorker(selectedTile, player);
                        else if (game.getAction() == ActionNames.MOVE || game.getAction() == ActionNames.BUILD) {
                            if (game.getValidTiles()[targetPosition.x][targetPosition.y])
                                performAction(selectedTile, player);
                            else if (game.getStepNumber() == 0)
                                selectWorker(selectedTile, player);
                        }
                    } else if (gameState == GameSate.WORKERS_PLACEMENT)
                        placeWorker(selectedTile, player);
                }
            }
        }
    }

    /**
     * remove view, notify disconnection and change gameState to GAME_OVER if it has not been
     * done already, then end the game if views is empty
     *
     * @param view it is the disconnected view
     */
    public synchronized void handleDisconnection(VirtualView view) {
        removeView(view);
        if (gameState != GameSate.GAME_OVER) {
            notifyDisconnection(view);
            gameState = GameSate.GAME_OVER;
        } else if (views.isEmpty())
            endGame();
    }

    /**
     * remove view, call server to assign it to a new game, then
     * end this game if views is empty
     *
     * @param view view that requires to be restarted
     */
    public synchronized void restartView(VirtualView view) {
        if (gameState == GameSate.GAME_OVER) {
            removeView(view);
            server.assignView(view);
            if (views.isEmpty())
                endGame();
        }
    }
    //                  GAME LOGIC
    //                  TURN LOGIC

    /**
     * @return true is turn is finished false if it's not
     */
    private boolean isTurnFinished() {
        int stepNumber = game.getStepNumber();
        boolean isPowerActive = game.isPowerActive();
        Card card = game.getActivePlayer().getCard();
        return !card.canMove(stepNumber, isPowerActive) && !card.canBuild(stepNumber, isPowerActive);
    }

    /**
     * do all the operations needed to set up the next turn. In specific addEffectOnOpponents,
     * resetCard so that it's not decorated anymore, set up game class parameters for next turn,
     * defeat a player if it has no more moves left and then notify it, clear validTiles,
     * save board so that can be restored when undo is activated
     *
     * @param currentStep the step that the active player has just performed
     */
    private void setUpNextTurn(Step currentStep, Step previousStep) {
        System.out.println("next turn\n");
        addEffectOnOpponents(currentStep, previousStep);

        game.getActivePlayer().resetCard();
        game.nextTurn();
        game.setAction(ActionNames.SELECT_WORKER);

        if (!hasPlayerMovesLeft()) {
            System.out.println("\nplayer has no moves left\n\n");
            VirtualView defeatedView = views.get(game.getPlayerIndex());
            notifyDefeat(defeatedView);
            removeView(defeatedView);

            game.nextTurn();

            if (game.getPlayers().size() == 1)
                notifyVictory(game.getPlayers().get(0).getPlayerNumber());
        }
        game.clearValidTiles();
        savedBoard = game.getBoardCopy();
    }

    /**
     * @return true if player has moves left, false otherwise
     */
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

    /**
     * if the active player's effect on others is active, decorate other players cards
     * with its decorator
     *
     * @param currentStep step that the active player has just performed
     */
    private void addEffectOnOpponents(Step currentStep, Step previousStep) {
        Player player = game.getActivePlayer();
        Card card = player.getCard();
        if (card.hasActiveDecorator(currentStep, previousStep, game.getBoard())) {
            System.out.println("applying decorator");
            for (Player opponent : game.getPlayers()) {
                if (!opponent.equals(player)) {
                    opponent.setCard(card.getDecorator(opponent.getCard()));
                }
            }
        }
    }
    //                  GAME LOGIC
    //                  SELECTIONS LOGIC

    /**
     * create a new worker, assign it to the player, and place it on the selected tile;
     * then change the gameState to MATCH or change turn if needed, save the board to restore after undos,
     * notify board
     *
     * @param selectedTile tile where a worker is to be placed
     * @param player       active player
     */
    private synchronized void placeWorker(Tile selectedTile, Player player) {
        if (selectedTile.getWorker() == null) {
            System.out.println("player: " + player.getPlayerNumber() + " is placing a worker");
            Worker worker = new Worker(player);
            selectedTile.setWorker(worker);
            player.addWorker(worker);
            if (player.getWorkers().size() == 2) {
                savedBoard = game.getBoardCopy();
                game.nextTurn();

                if (game.getPlayerIndex() == 0) {
                    game.setAction(ActionNames.SELECT_WORKER);
                    gameState = GameSate.MATCH;
                } else
                    game.setAction(ActionNames.PLACE_WORKERS);
            }
            notifyBoard();
        }
    }

    /**
     * if a the selected tile contains a valid worker select it and update validTiles,
     * else deselect any selected worker and clear validTiles; then notify board
     *
     * @param selectedTile tile where a worker should be selected
     * @param player       active player
     */
    private synchronized void selectWorker(Tile selectedTile, Player player) {
        System.out.println("player: " + player.getPlayerNumber() + " is selecting a worker");
        if (selectedTile.getWorker() != null && player.equals(selectedTile.getWorker().getOwner())) {
            game.setSelectedWorkerTile(selectedTile);
            updateValidTiles();
        } else {
            game.setAction(ActionNames.SELECT_WORKER);
            game.setSelectedWorkerTile(null);
            game.clearValidTiles();
        }
        notifyBoard();
    }

    /**
     * perform a build or a move in the target tile and check if the player has won with that move,
     * if it has notifyVictory and set gameState to GAME_OVER, if not: set next step, activate worker power
     * if it's stepNumber 2 and it hasChoice, set up next turn if turn is finished, else just update valid
     * tiles, notifyBoard. While this is done if a valid undo message arrives the undo function is called
     * and the undo is performed instead
     *
     * @param targetTile controlled tile where the selected worker is performing the action
     * @param player     active player
     */
    private synchronized void performAction(Tile targetTile, Player player) {
        System.out.println("player: " + player.getPlayerNumber() + " is moving/building with a worker");
        Step currentStep = new Step(game.getSelectedWorkerTile(), targetTile, game.isPowerActive());
        Step previousStep = player.getPreviousStep();
        Card card = player.getCard();

        player.setPreviousStep(currentStep);

        if (game.getAction() == ActionNames.MOVE) {
            card.move(currentStep, previousStep, game.getBoard());
            game.setSelectedWorkerTile(targetTile);
        } else if (game.getAction() == ActionNames.BUILD)
            card.build(currentStep, previousStep, game.getBoard());

        if (game.getAction() == ActionNames.MOVE && card.checkWin(currentStep, game.getBoard())) {
            notifyVictory(player.getPlayerNumber());
            gameState = GameSate.GAME_OVER;
        } else {

            game.nextStep();

            if (game.getStepNumber() == 2 && game.hasChoice())
                game.setPowerActive(true);

            if (isTurnFinished()) {
                game.clearValidTiles();
                notifyBoard();

                if (isUndoArrived())
                    undo();
                else {
                    setUpNextTurn(currentStep, previousStep);
                    notifyBoard();
                }
            } else {
                updateValidTiles();
                notifyBoard();

                if (isUndoArrived())
                    undo();
            }
        }
    }

    /**
     * sets some variables used for undo functionality
     *
     * @return true if a valid undo is arrived else false
     */
    private boolean isUndoArrived() {
        System.out.println("checking is undo arrived");
        hasUndoArrived = false;
        isUndoPossible = true;
        hasSkippedUndo = false;
        long finishTime = System.currentTimeMillis() + 5000;
        while (System.currentTimeMillis() < finishTime && !hasSkippedUndo) {
            if (hasUndoArrived)
                return true;
        }
        isUndoPossible = false;
        System.out.println("undo no more possible");
        return false;
    }

    /**
     * perform undo by restoring the saved board and restarting the current turn
     */
    private void undo() {
        System.out.println("undoing");
        game.undo(savedBoard);
        savedBoard = game.getBoardCopy();
        game.clearValidTiles();
        notifyBoard();
    }
    //                 GAME LOGIC
    //                  VALID TILES LOGIC

    /**
     * updates the validTiles
     */
    private void updateValidTiles() {
        System.out.println("calculating validTiles");
        boolean isPowerActive = game.isPowerActive();
        Tile workerTile = game.getSelectedWorkerTile();
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

    /**
     * @return the validTiles of the possible move that the selectedPlayer can do
     */
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

    /**
     * @return the validTiles of the possible builds that the selectedPlayer can do
     */
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
