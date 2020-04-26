package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

public class WaitMessage {
    int queueLength;

    public WaitMessage(int queueLength) {
        this.queueLength = queueLength;
    }
}
