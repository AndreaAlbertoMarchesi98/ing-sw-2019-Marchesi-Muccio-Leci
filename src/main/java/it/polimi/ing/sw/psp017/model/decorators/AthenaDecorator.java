package it.polimi.ing.sw.psp017.model.decorators;

import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.model.Tile;
import it.polimi.ing.sw.psp017.model.deck.Athena;

public class AthenaDecorator extends CardDecorator {

    Athena athenaCard;

    public AthenaDecorator(Card newCard, Card referenceCard) {
        super(newCard, referenceCard);
        athenaCard = (Athena) referenceCard;
    }

    @Override
    public boolean isValidMove(Tile currentTile, Tile targetTile) {
        if (athenaCard.hasMovedUp()) {
            if (targetTile.getLevel() > currentTile.getLevel())
                return false;
            else
                return super.isValidMove(currentTile, targetTile);
        }
        else
            return super.isValidMove(currentTile, targetTile);
    }
}