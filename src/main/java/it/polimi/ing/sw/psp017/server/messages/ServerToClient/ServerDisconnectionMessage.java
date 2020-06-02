package it.polimi.ing.sw.psp017.server.messages.ServerToClient;

import java.io.Serializable;

public class ServerDisconnectionMessage implements Serializable {
    public int disconnectedPlayerNumber;

    public ServerDisconnectionMessage(int disconnectedPlayerNumber) {
        this.disconnectedPlayerNumber = disconnectedPlayerNumber;
    }
}
