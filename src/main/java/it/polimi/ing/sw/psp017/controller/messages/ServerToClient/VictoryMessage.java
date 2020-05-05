package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

public class VictoryMessage {
    public String winnerName;
    public int playerNumber;

    public VictoryMessage(String winnerName,int playerNumber)
    {
        this.winnerName = winnerName;
        this.playerNumber = playerNumber;
    }
}
