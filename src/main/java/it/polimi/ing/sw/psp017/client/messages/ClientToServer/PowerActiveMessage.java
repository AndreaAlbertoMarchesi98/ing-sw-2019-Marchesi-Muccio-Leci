package it.polimi.ing.sw.psp017.client.messages.ClientToServer;

import java.io.Serializable;

public class PowerActiveMessage implements Serializable {
    public boolean isPowerActive;

    public PowerActiveMessage(boolean isPowerActive) {
        this.isPowerActive = isPowerActive;
    }
}
