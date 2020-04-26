package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

public class ValidTilesMessage {
    public boolean[][] validTiles;
    public boolean isMove;

    public ValidTilesMessage(boolean[][] validTiles){
        this.validTiles = validTiles;
    }
}
