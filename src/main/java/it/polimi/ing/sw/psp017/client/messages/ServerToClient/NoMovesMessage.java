package it.polimi.ing.sw.psp017.client.messages.ServerToClient;

import java.io.Serializable;

public class NoMovesMessage implements Serializable {
    public int playerNumber;

    public NoMovesMessage(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
