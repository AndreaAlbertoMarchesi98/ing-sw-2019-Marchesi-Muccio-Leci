


package it.polimi.ing.sw.psp017.controller.messages.ServerToClient;

import it.polimi.ing.sw.psp017.controller.server.Lobby;
import it.polimi.ing.sw.psp017.view.GodName;

import java.io.Serializable;
import java.util.ArrayList;

public class LobbyMessage implements Serializable {


    //public String choosingNickname
    public int choosingPlayerNumber;
    public ArrayList<String> players;
    public ArrayList<GodName> chosenCards;
    public ArrayList<GodName> availableCards;

    public LobbyMessage(Lobby lobby) {
        this.players = lobby.getPlayersNames();
        this.chosenCards = lobby.getChosenCards();
        this.availableCards = lobby.getCards();
        //this.choosingPlayerNumber = lobby.getCards().size() - lobby.getChosenCards().size();
        this.choosingPlayerNumber = lobby.getPlayerCount() - lobby.getChosenCards().size();
        System.out.println("choosen" + chosenCards);
    }


}


