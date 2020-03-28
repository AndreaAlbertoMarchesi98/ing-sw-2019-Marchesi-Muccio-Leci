package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.controller.CardFactory;
import it.polimi.ing.sw.psp017.model.Card;
import it.polimi.ing.sw.psp017.view.GodName;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        Card test = CardFactory.getCard(GodName.APOLLO);
        test.move();
    }

}
