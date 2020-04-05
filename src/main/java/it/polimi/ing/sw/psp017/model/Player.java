package it.polimi.ing.sw.psp017.model;

import java.util.ArrayList;

public class Player {
    private String nickname;
    private Color color;
    private ArrayList<Worker> workers =new ArrayList<Worker>();
    private Card card;

    public Player(String nickname, Color color){
        this.nickname = nickname;
        this.color = color;
    }


    public enum Color {
        RED, BLUE, GREEN
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<Worker> workers) {
        this.workers = workers;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public void addWorker(Worker w){
        workers.add(w);
    }
}
