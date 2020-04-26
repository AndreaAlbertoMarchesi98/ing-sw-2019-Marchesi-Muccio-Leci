package it.polimi.ing.sw.psp017.model.deck;

import it.polimi.ing.sw.psp017.model.*;

import it.polimi.ing.sw.psp017.model.decorators.*;
import it.polimi.ing.sw.psp017.view.GodName;

public class Athena extends BaseCard{

    public Athena(){
        name=GodName.ATHENA;
    }

    @Override
    public boolean hasActiveDecorator(Step currentStep, Step previousStep, Board board){
        return previousStep.getTargetTile().getLevel()>previousStep.getCurrentTile().getLevel();
    }

    @Override
    public Card getDecorator(Card cardToDecorate){
        return new AthenaDecorator(cardToDecorate);
    }



}
