


package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.controller.server.Lobby;
import it.polimi.ing.sw.psp017.view.GodName;

import java.io.Serializable;
import java.util.ArrayList;

public class LobbyMessage implements Serializable {
    public int choosingPlayerNumber;
    public ArrayList<String> players; //numero di giocatori definitivo
    public ArrayList<GodName> chosenCards;//carte che puoi scegliere
    public ArrayList<GodName> availableCards;//carte scelte dal primo player

    public LobbyMessage(Lobby lobby) {
        this.players = lobby.getPlayersNames();
        this.chosenCards = new ArrayList<>(lobby.getChosenCards());
        this.availableCards = new ArrayList<>(lobby.getAvailableCards());
        this.choosingPlayerNumber = lobby.getChoosingPlayerNumber();
    }


}


