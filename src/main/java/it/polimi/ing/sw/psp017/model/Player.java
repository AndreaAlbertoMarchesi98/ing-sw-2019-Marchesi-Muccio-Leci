package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;
import java.util.Random;


public class Player {

    private String nickname;
    private int playerIndex;
    private Color color;
    private ArrayList<Worker> workers;
    private Card card;

    private Step previousStep;

    public Player(String nickname) {
        this.nickname = nickname;
        this.color = Color.getRandomColor();
        workers = new ArrayList<Worker>();
    }


    public enum Color {
        RED, BLUE, GREEN;

        public static Color getRandomColor() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Color getColor() {
        return color;
    }

    public Step getPreviousStep() {
        return previousStep;
    }

    public void setPreviousStep(Step previousStep) {
        this.previousStep = previousStep;
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
