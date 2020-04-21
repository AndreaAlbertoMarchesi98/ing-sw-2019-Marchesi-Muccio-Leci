package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class LobbyMessage {
    public ArrayList<String> players;
    public ArrayList<GodName> cards;

    public LobbyMessage(ArrayList<String> players, ArrayList<GodName> cards) {
        this.players = players;
        this.cards = cards;
    }
}
