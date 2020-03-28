package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Card;

public class Apollo implements Card {

    @Override
    public void move() {
        System.out.println("dentro a move");
    }

    @Override
    public void build() {

    }

    @Override
    public void checkWin() {

    }
}
