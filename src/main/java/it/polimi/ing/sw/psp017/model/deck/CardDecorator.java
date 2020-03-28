package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.Card;

abstract public class CardDecorator implements Card {
    protected Card tempCard;

    public  CardDecorator (Card newCard){
        tempCard = newCard;
    }


    @Override
    public void move() {
     tempCard.move();
    }

    @Override
    public void build() {

    }

    @Override
    public void checkWin() {

    }
}
