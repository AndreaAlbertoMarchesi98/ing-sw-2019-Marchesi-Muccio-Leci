package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import java.io.Serializable;

public class ValidTilesMessage implements Serializable {
    public boolean[][] validTiles;
    public boolean isMove;

    public ValidTilesMessage(boolean[][] validTiles){
        this.validTiles = validTiles;
    }
}
