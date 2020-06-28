package it.polimi.ing.sw.psp017.server.model;

import java.util.ArrayList;
import java.util.Random;


public class Player {
    public static final int workersNumber = 2;
    private final String nickname;
    private int playerNumber;
    private final ArrayList<Worker> workers;
    private volatile Card card;
    private Card originalCard;

    private Step previousStep;

    public Player(String nickname) {
        this.nickname = nickname;
        workers = new ArrayList<>();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void reset() {
        card = null;
        originalCard = null;
        previousStep = null;
        workers.clear();
    }

    public void resetCard() {
        card = originalCard;
    }

    public void setOriginalCard(Card originalCard) {
        this.originalCard = originalCard;
    }

    public Step getPreviousStep() {
        return previousStep;
    }

    public void setPreviousStep(Step previousStep) {
        if(previousStep!=null) {
            Tile currentTile = new Tile(previousStep.getCurrentTile().getPosition());
            currentTile.setLevel(previousStep.getCurrentTile().getLevel());
            Tile targetTile = new Tile(previousStep.getTargetTile().getPosition());
            targetTile.setLevel(previousStep.getTargetTile().getLevel());
            this.previousStep = new Step(currentTile, targetTile, previousStep.isPowerActive());
        }
        else
            this.previousStep=null;
    }

    public String getNickname() {
        return nickname;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void addWorker(Worker w) {
        workers.add(w);
    }
}
