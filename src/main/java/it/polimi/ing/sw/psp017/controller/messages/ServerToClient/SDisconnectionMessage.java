package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import java.io.Serializable;

public class SDisconnectionMessage implements Serializable {
    public int disconnectedPlayerNumber;

    public SDisconnectionMessage(int disconnectedPlayerNumber) {
        this.disconnectedPlayerNumber = disconnectedPlayerNumber;
    }
}
