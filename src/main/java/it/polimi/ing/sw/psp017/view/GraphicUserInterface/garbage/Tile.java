package it.polimi.ing.sw.psp017.view.GraphicUserInterface.garbage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JPanel {

    int x;
    int y;
    JButton tile;

    public Tile (int x,int y)
    {
        this.x = x;
        this.y = y;
        tile = new JButton();
        tile.setSize(50,50);
        ListenerForTile listener = new ListenerForTile();
        tile.addActionListener(listener);
        add(tile);
    }

   public class ListenerForTile implements ActionListener{

       @Override
       public void actionPerformed(ActionEvent e) {


       }
   }
}
