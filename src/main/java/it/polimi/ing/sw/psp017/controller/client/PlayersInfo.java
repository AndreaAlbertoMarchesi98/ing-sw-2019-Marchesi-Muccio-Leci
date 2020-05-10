package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.view.GodName;

public class PlayersInfo {


    String name;
    int playerNumber;
    GodName cards;

    public PlayersInfo(String name, int playerNumber,GodName cards) {
        this.name = name;
        this.playerNumber = playerNumber;
        this.cards = cards;
    }


}
