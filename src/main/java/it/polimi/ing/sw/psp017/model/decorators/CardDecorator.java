package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Tile;

abstract public class CardDecorator implements Card {
    protected Card tempCard;

    public  CardDecorator (Card newCard){
        tempCard = newCard;
    }


    public boolean isValidMove(Tile currentTile, Tile targetTile){
        return tempCard.isValidMove(currentTile,targetTile);
    }

    public boolean isValidBuilding(Tile targetTile){
        return tempCard.isValidBuilding(targetTile);
    }
    public boolean checkWin(Tile currentTile, Tile targetTile){
        return tempCard.checkWin(currentTile,targetTile);
    }

    public void move(Tile currentTile, Tile targetTile){
        tempCard.move(currentTile,targetTile);
    }

    public void build(Tile targetTile){
        tempCard.build(targetTile);
    }

    public boolean hasDecorator(){
        return tempCard.hasDecorator();
    }

}
