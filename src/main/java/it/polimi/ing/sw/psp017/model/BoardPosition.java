package it.polimi.ing.sw.psp017.model;

public class BoardPosition {
    public int x;
    public int y;

    public static BoardPosition sumPositions(BoardPosition boardPosition1, BoardPosition boardPosition2){
        BoardPosition bp = new BoardPosition();
        bp.x= boardPosition1.x + boardPosition2.x;
        bp.y=boardPosition1.y + boardPosition2.y;
        return bp;
    }


}
