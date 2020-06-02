package it.polimi.ing.sw.psp017.server.controller;

import it.polimi.ing.sw.psp017.server.model.Player;
import it.polimi.ing.sw.psp017.client.view.GodName;

import java.util.ArrayList;

public class Lobby {
    private final ArrayList<Player> players;
    private final ArrayList<GodName> availableCards;
    private final ArrayList<GodName> chosenCards;
    private final int expectedPlayersCount;

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
