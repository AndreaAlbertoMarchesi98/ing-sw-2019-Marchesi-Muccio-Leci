package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<Player> players;
    private ArrayList<GodName> availableCards;
    private ArrayList<GodName> chosenCards;
    private int expectedPlayersCount;

    public Lobby(ArrayList<GodName> godNames){
        chosenCards = new ArrayList<>();
        expectedPlayersCount = godNames.size();
        availableCards = godNames;
        players = new ArrayList<>();
    }

    public ArrayList<String> getPlayersNames(){
        ArrayList<String> names = new ArrayList<>();
        for(Player player : players)
            names.add(player.getNickname());
        return names;
    }

    public boolean isFull(){
        return players.size() == expectedPlayersCount; }


    public void addPlayer(Player player) {
        players.add(player);
    }

    public ArrayList<GodName> getAvailableCards() {
        return availableCards;
    }

    public int getChoosingPlayerNumber(){
        return availableCards.size();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<GodName> getChosenCards() {
        return chosenCards;
    }

    public void addChosenCard(GodName chosenCard) {
        chosenCards.add(chosenCard);
    }
}
