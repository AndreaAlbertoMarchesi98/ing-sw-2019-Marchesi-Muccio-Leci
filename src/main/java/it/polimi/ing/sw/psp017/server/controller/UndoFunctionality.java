package it.polimi.ing.sw.psp017.server.controller;

import it.polimi.ing.sw.psp017.server.model.Board;
import it.polimi.ing.sw.psp017.server.model.Game;

public class UndoFunctionality {
    private volatile boolean undoPossible;
    private volatile boolean hasUndoArrived;
    private volatile boolean hasSkippedUndo;
    private Board savedBoard;
    private final int undoWaitTime;

    /**
     * sets undoWaitTime
     *
     * @param undoWaitTime is for how long is possible to undo
     */
    public UndoFunctionality(int undoWaitTime) {
        this.undoWaitTime = undoWaitTime;
    }

    /**
     * sets some variables used for undo functionality
     *
     * @return true if a valid undo is arrived else false
     */
    public boolean isUndoArrived() {
        System.out.println("checking is undo arrived");
        hasUndoArrived = false;
        undoPossible = true;
        hasSkippedUndo = false;
        long finishTime = System.currentTimeMillis() + undoWaitTime;
        while (System.currentTimeMillis() < finishTime && !hasSkippedUndo) {
            if (hasUndoArrived)
                return true;
        }
        undoPossible = false;
        System.out.println("undo no more possible");
        return false;
    }

    /**
     * @return true if undo is possible false if not
     */
    public synchronized boolean isUndoPossible() {
        return undoPossible;
    }

    /**
     * set hasSkippedUndo to true
     */
    public synchronized void skipUndo() {
        hasSkippedUndo = true;
    }

    /**
     * if undo is possible set hasUndoArrived to true
     */
    public synchronized void receiveUndo() {
        if (undoPossible) {
            System.out.println("undo has arrived");
            hasUndoArrived = true;
        }
    }

    /**
     * perform undo by restoring the saved board and restarting the current turn
     */
    public void undo(Game game) {
        System.out.println("undoing");
        game.undo(savedBoard);
        savedBoard = game.getBoardCopy();
        game.clearValidTiles();
    }

    /**
     * saves the board, so that it can be restored on undo
     *
     * @param board board to be saved
     */
    public void saveBoard(Board board) {
        savedBoard = board;
    }
}
