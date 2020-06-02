package it.polimi.ing.sw.psp017.server.messages.ServerToClient;

import java.io.Serializable;

public class NoMovesMessage implements Serializable {
    public int playerNumber;

    public NoMovesMessage(int playerNumber) {
        System.out.println("\n\n\n\n\n\n"+playerNumber+"\n\n\n\n\n\n\n\n");
        this.playerNumber = playerNumber;
    }
}
