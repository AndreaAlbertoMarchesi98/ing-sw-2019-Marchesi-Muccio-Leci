package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import javax.swing.*;
import java.awt.*;

public class Gioco extends JFrame {

    private Board board;
    private JFrame nord;


    public Gioco()
    {
        super("Santorini");
        setSize(500,500);
        setLayout(new BorderLayout());
        board = new Board();
        add(board,BorderLayout.CENTER);
        nord = new JFrame();
        setVisible(true);

        JPanel nord = new JPanel();
        nord.setSize(200,500);
        JTextArea testo = new JTextArea("prova");
        nord.add(testo);
        nord.setVisible(true);
        add(nord,BorderLayout.NORTH);


        this.pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
