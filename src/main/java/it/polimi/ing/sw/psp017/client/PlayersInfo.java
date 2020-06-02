package it.polimi.ing.sw.psp017.client;

import it.polimi.ing.sw.psp017.client.view.GodName;

public class PlayersInfo {


    public String name;
    public int playerNumber;
    public GodName card;

    public PlayersInfo(String name, int playerNumber,GodName cards) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.card = cards;
    }


}
