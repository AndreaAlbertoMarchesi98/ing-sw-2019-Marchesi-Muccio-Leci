package it.polimi.ing.sw.psp017.server.controller;

import it.polimi.ing.sw.psp017.server.model.*;
import it.polimi.ing.sw.psp017.server.model.deck.*;
import it.polimi.ing.sw.psp017.client.view.GodName;


public  class CardFactory {

    public static Card getCard(GodName godName){

        switch(godName){
            case APOLLO:
                return new Apollo();
            case ARTEMIS:
                return new Artemis();
            case ATHENA:
                return new Athena();
            case ATLAS:
                return new Atlas();
            case DEMETER:
                return new Demeter();
            case HEPHAESTUS:
                return new Hephaestus();
            case MINOTAUR:
                return new Minotaur();
            case PAN:
                return new Pan();
            case PROMETHEUS:
                return new Prometheus();
            default:
                System.out.println("not existing card");
                return null;
        }
    }


}
