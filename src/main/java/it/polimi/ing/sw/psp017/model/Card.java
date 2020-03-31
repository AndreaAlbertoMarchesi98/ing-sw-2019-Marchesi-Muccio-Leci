package it.polimi.ing.sw.psp017.model;
/*ROBE DA CHIEDERE
1)caso minotar
*/
public interface Card {
    boolean hasDecorator();

    boolean canMove(int step);

    boolean canBuild(int step);

    boolean isValidMove(Tile currentTile, Tile targetTile);

    boolean isValidBuilding(Tile targetTile);

    boolean checkWin(Tile currentTile, Tile targetTile);

    void move(Tile currentTile, Tile targetTile);

    void build(Tile targetTile);
}
