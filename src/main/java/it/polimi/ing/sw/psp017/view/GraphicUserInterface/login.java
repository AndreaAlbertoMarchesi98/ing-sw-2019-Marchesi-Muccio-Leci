package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import javax.swing.*;
import java.awt.*;

public class login extends JPanel {

    private JTextArea nickname;
    private JTextField login;
    private JButton enter;

    public static void main(String[] args) {
        new login();
    }
    public login ()
    {
        this.setSize(500,500);

        setLayout(new FlowLayout());
        nickname = new JTextArea("insert your nickname :");
        enter = new JButton("enter");
        login = new JTextField();
        add(nickname);
        add(login);
        add(enter);


        setVisible(true);
    }

}
