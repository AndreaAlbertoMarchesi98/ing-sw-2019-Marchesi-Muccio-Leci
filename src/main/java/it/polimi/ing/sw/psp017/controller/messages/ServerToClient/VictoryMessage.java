package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import java.io.Serializable;

public class VictoryMessage implements Serializable {
    public int winnerNumber;

    public VictoryMessage(int winnerNumber) {
        this.winnerNumber = winnerNumber;
    }
}
