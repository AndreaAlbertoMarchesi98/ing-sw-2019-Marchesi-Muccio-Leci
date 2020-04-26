package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.controller.server.Lobby;
import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class LobbyMessage {
    public ArrayList<String> players;
    public ArrayList<GodName> cards;

    public LobbyMessage(Lobby lobby) {
        this.players = lobby.getPlayersNames();
        this.cards = lobby.getRemainingCards();
    }
}
