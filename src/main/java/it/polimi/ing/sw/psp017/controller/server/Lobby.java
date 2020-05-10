package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<Player> players;
    private ArrayList<GodName> availableCards;
    private ArrayList<GodName> chosenCards;
    private ArrayList<GodName> cards;
    private int playersCount;
    private int expectedPlayersCount;

    public Lobby(ArrayList<GodName> godNames){
        chosenCards = new ArrayList<>();
        expectedPlayersCount = godNames.size();
        cards = godNames;
        availableCards = cards;
        playersCount = 0;
        players = new ArrayList<>();
    }

    public ArrayList<String> getPlayersNames(){
        ArrayList<String> names = new ArrayList<>();
        for(Player player : players)
            names.add(player.getNickname());
        return names;
    }

    public boolean isPlayerTurn(Player player){
        return true;//to be implemented
    }
    public int getPlayerCount() {
        return playersCount;
    }

    public int getExpectedPlayersCount() {
        return expectedPlayersCount;
    }


    public void addPlayer(Player player) {
        players.add(player);
        playersCount++;
    }

    public ArrayList<GodName> getAvailableCards() {
        return availableCards;
    }

    public ArrayList<GodName> getCards() {
        return cards;
    }

    public int getChoosingPlayerIndex(){
        return availableCards.size() - 1;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<GodName> getChosenCards() {
        return chosenCards;
    }

    public void addChosenCards(GodName chosenCard) {
        chosenCards.add(chosenCard);
    }
}
