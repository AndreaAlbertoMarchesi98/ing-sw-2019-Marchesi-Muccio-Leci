package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.view.GodName;

public class PlayersInfo {


    String name;
    int playerNumber;

    GodName cards;

    public PlayersInfo(String name, int playerNumber) {
        this.name = name;
        this.playerNumber = playerNumber;
    }



    public String getName() {
        return name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }


    public GodName getCards() {
        return cards;
    }

    public void setCards(GodName cards) {
        this.cards = cards;
    }







}
