package it.polimi.ing.sw.psp017.controller.server;

import it.polimi.ing.sw.psp017.model.Player;
import it.polimi.ing.sw.psp017.view.GodName;

import java.util.ArrayList;

public class Lobby {
    private ArrayList<Player> players;
    private ArrayList<GodName> remainingCards;
    private int playersCount;
    private int expectedPlayersCount;
    private int turnToChoose;

    public Lobby(Player firstPlayer, ArrayList<GodName> godNames){
        players.add(firstPlayer);
        this.remainingCards = godNames;
        expectedPlayersCount = godNames.size();
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

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        playersCount++;
    }

    public ArrayList<GodName> getRemainingCards() {
        return remainingCards;
    }
}
