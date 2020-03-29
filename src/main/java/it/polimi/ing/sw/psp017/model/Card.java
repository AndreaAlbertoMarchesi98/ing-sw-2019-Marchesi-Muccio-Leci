package it.polimi.ing.sw.psp017.model;
/*ROBE DA CHIEDERE
1)worker vuoto o null pointer
2)board classe statica
3)azioni multiple
4)carte con ereditarieta nel model
5)prometeo model controller, scelat move or build in model o controller
6)risondanza metodo hasDecorator nei decorators
*/
public interface Card {
    boolean hasDecorator();

    boolean isValidMove(Tile currentTile, Tile targetTile);

    boolean isValidBuilding(Tile targetTile);

    boolean checkWin(Tile currentTile, Tile targetTile);

    void move(Tile currentTile, Tile targetTile);

    void build(Tile targetTile);
}
