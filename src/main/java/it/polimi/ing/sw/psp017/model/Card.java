package it.polimi.ing.sw.psp017.model;

public interface Card {
    boolean isValidMove(Tile currentTile, Tile targetTile);

    boolean isValidBuilding(Tile targetTile);

    boolean checkWin(Tile currentTile, Tile targetTile);

    void move(Tile currentTile, Tile targetTile);

    void build(Tile targetTile);
}
