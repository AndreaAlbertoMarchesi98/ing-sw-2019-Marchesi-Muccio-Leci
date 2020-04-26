package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import java.io.Serializable;

public class WaitMessage implements Serializable {
    int queueLength;

    public WaitMessage(int queueLength) {
        this.queueLength = queueLength;
    }
}
