package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {

    private Tile[][] board = new Tile[5][5];
    private JButton btn;


    public Board()
    {
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(new Dimension(300,300));
        setLayout(new GridLayout(5,5));

        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 5; j++)
            {
                Tile temp= new Tile(i,j);
                add(board[i][j] = new Tile(i,j));

            }
        }

    }
}
