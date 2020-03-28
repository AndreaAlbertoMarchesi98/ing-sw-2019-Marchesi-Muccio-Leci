package it.polimi.ing.sw.psp017.model;

public interface Card {
    boolean isValidMove(BoardPosition direction, Worker worker);
     boolean isValidBuilding(BoardPosition direction, Worker worker) ;
     boolean checkWin(Worker worker) ;
    void move(BoardPosition direction, Worker worker);

    }
