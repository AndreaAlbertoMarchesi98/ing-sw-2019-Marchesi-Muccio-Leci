package it.polimi.ing.sw.psp017.view.GraphicUserInterface;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.AuthenticationMessage;
import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.KGradientPanel;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class LoginPanel extends JPanel {
        private JPanel bottomPanel;
        private JButton cliButton;
        private JLabel infoLabel;
        private JPanel inputPanel;
        private KGradientPanel kGradientPanel;
        private JLabel logoLabel;
        private JButton nickButton;
        private JTextField nicknameField;
        private Client client;

        public LoginPanel(Client client){
            initComponents();
            this.client = client;
        }

        private void initComponents() {
            this.setMinimumSize(new Dimension(800,1000));

            kGradientPanel = new KGradientPanel();
            logoLabel = new JLabel();
            infoLabel = new JLabel();
            inputPanel = new JPanel()
    /*        {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(image, 3, 4, this);
                }
            }*/;
            nicknameField = new JTextField();
            nickButton = new JButton();
            bottomPanel = new JPanel();
            cliButton = new JButton();


            kGradientPanel.setkEndColor(new Color(255, 255, 153));
            kGradientPanel.setkStartColor(new Color(0, 204, 102));

            kGradientPanel.setLayout(new GridLayout(4, 1,400,0));


            logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            logoLabel.setSize(1200,200);
            logoLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("logonome.png")));
            logoLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            kGradientPanel.add(logoLabel);


            infoLabel.setFont(new Font("Tahoma", 1, 36));
            infoLabel.setForeground(Color.white);
            infoLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setText("Insert your nickname");
            JPanel infoPanel = new JPanel();
            infoPanel.setOpaque(false);
            infoPanel.setLayout(new FlowLayout(FlowLayout.CENTER,70,10));
            infoPanel.add(infoLabel);
            kGradientPanel.add(infoPanel);
            inputPanel.setOpaque(false);

            nicknameField.setFont(new Font("Tahoma", 2, 36));
            nicknameField.setForeground(Color.BLUE);
            nicknameField.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            nicknameField.setPreferredSize(new Dimension(300, 70));
            nicknameField.setText("");
            nicknameField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER && !nicknameField.getText().equals("")) {
                                sendNick();

                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {
                }
            });

            inputPanel.add(nicknameField);
            nickButton.setBackground(new Color(50, 153, 80));
            nickButton.setFont(new Font("Tahoma", 2, 24));
            nickButton.setForeground(new Color(0, 102, 51));
            nickButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("STUFF/playButton.png")));
            //nickButton.setText("Play");
//            nickButton.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
            nickButton.setOpaque(false);
            nickButton.setBorder(null);
            nickButton.setPreferredSize(new Dimension(82, 85));


            nickButton.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!nicknameField.getText().equals("")){
                        sendNick();
                    }

                }
            });

            inputPanel.add(nickButton);

           kGradientPanel.add(inputPanel);

            bottomPanel.setOpaque(false);
            bottomPanel.setLayout(new GridLayout(4,1, 200,20));

            cliButton.setBackground(new Color(204, 255, 204));
            cliButton.setFont(new Font("Tahoma", 0, 14));
            cliButton.setForeground(new Color(204, 102, 0));
            cliButton.setText("Continue with CLI");
            cliButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
            cliButton.setOpaque(false);
            cliButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Object[] option = {"Confirm", "Cancel"};
                    int n = JOptionPane.showOptionDialog(cliButton.getRootPane(), "Are you sure you want to switch to the command line? Gui will be closed.", "Close Graphic User Interface", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
                    ;
                    if (n == JOptionPane.YES_OPTION) {
                        cliButton.getRootPane().setVisible(false);
                        System.out.println("Continue here...");
                        client.setView(new CLI(client));
                        client.getView().updateLoginScreen(null);
                    }

                }
            });
            bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING, 40,40));
            bottomPanel.add(cliButton);
            kGradientPanel.add(bottomPanel);
          // kGradientPanel.setPreferredSize(new Dimension(2000, 1000));
            this.setLayout(new BorderLayout());
           // this.setPreferredSize(new Dimension(1200, 1000));
            this.add(kGradientPanel, BorderLayout.CENTER);



        }

        private void sendNick(){
            nicknameField.setEnabled(false);
            System.out.println(nicknameField.getText());
            client.setNickname(nicknameField.getText());
            nickButton.setEnabled(false);
            cliButton.setEnabled(false);
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    nickButton.setText("");
                    infoLabel.setText("Waiting for Server response...");
                    nickButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("ajax-loader.gif")));
                    client.getNetworkHandler().sendMessage(new AuthenticationMessage(nicknameField.getText()));
                }
            });

          /*  mainPanel.revalidate();
            mainPanel.repaint();
            mainFrame.repaint();*/
        }



}
