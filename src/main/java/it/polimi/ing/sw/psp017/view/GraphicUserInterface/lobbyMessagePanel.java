package it.polimi.ing.sw.psp017.view.GraphicUserInterface;



import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.CardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.view.GodName;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class lobbyMessagePanel extends JPanel {


    private JPanel cardSelection_JPanel;
    private JLabel jLabel1;
    private KGradientPanel kGradientPanel1;
    private JButton playButton_JButton;
    private JPanel selectCardPanel_JPanel;
    private JPanel selectPanel_JPanel;
    private Client client;





    public lobbyMessagePanel(final LobbyMessage lobbyMessage, final Client client, final JFrame actualFrame) {



        this.client = client;
        kGradientPanel1 = new KGradientPanel();
        selectCardPanel_JPanel = new JPanel();
        jLabel1 = new JLabel();
        cardSelection_JPanel = new JPanel();

        final ArrayList<JToggleButton> cardsButton = new ArrayList<>();
        selectPanel_JPanel = new JPanel();
        playButton_JButton = new JButton();


        kGradientPanel1.setkEndColor(new java.awt.Color(255, 102, 102));
        kGradientPanel1.setSize(1000,800);
        selectCardPanel_JPanel.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("player " + lobbyMessage.choosingPlayerNumber + "is selecting a card");
        selectCardPanel_JPanel.add(jLabel1);

        cardSelection_JPanel.setOpaque(false);
        cardSelection_JPanel.setLayout(new java.awt.GridLayout(1, 0));


        for(int i = 0; i < lobbyMessage.availableCards.size();i++)
        {
            JToggleButton temp = new JToggleButton();
            temp.setContentAreaFilled(false);
            temp.setName(lobbyMessage.availableCards.get(i).toString());
            temp.setIcon(GodView.getCard(lobbyMessage.availableCards.get(i)).getIcon());
            temp.setBackground(null);

            if (client.getPlayerNumber() == 0) {
                client.setPlayerNumber(lobbyMessage.players.indexOf(client.getNickname()) + 1);

            }
            if(client.getPlayerNumber() != lobbyMessage.choosingPlayerNumber)
            {
                temp.setEnabled(false);
            }
            else
            {
                temp.setEnabled(true);
                jLabel1.setText("Choose your card");
                temp.setContentAreaFilled(true);
                temp.setBackground(new Color(200,80,254));
                //temp.setBackground(new Color(.1f,.1f,.1f, .1f));
            }
            cardsButton.add(temp);
            cardSelection_JPanel.add(temp);


        }
        ButtonGroup group = new ButtonGroup();

        for(final JToggleButton toggleButton : cardsButton){
            group.add(toggleButton);
            toggleButton.addMouseListener((new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() >= 2){
                        JDialog popUp = new JDialog(actualFrame);
                        JLabel playerDescription = new JLabel();
                        playerDescription.setIcon(GodView.getCard(GodName.valueOf((toggleButton.getName()))).getIconDescription());
                        popUp.add(playerDescription);
                        popUp.setResizable(false);
                        popUp.setVisible(true);
                        popUp.setSize(507,278);
                        popUp.setLocationRelativeTo(null);
                    }

                    else {

                            client.setCard(GodName.valueOf(toggleButton.getName()));
                            playButton_JButton.setEnabled(true);
                            playButton_JButton.setName(toggleButton.getName());



                    }

                }


                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }

            }));

        }



        selectPanel_JPanel.setOpaque(false);

        playButton_JButton.setBackground(new java.awt.Color(0, 153, 153));
        playButton_JButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/playButton.png")));
        playButton_JButton.setToolTipText("");
        playButton_JButton.setAutoscrolls(true);
        playButton_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton_JButtonActionPerformed(evt);
            }
        });
        selectPanel_JPanel.add(playButton_JButton);

        GroupLayout kGradientPanel1Layout = new GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(selectCardPanel_JPanel, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                        .addComponent(cardSelection_JPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addComponent(selectPanel_JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(selectCardPanel_JPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(cardSelection_JPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(selectPanel_JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(43, Short.MAX_VALUE))
        );
        this.setLayout(new BorderLayout());
        this.add(kGradientPanel1, BorderLayout.CENTER);
    }

    private void initialiseButton(LobbyMessage lobbyMessage, JButton jButton) {
    }


    private void playButton_JButtonActionPerformed(java.awt.event.ActionEvent evt) {
       if(evt.getSource() == playButton_JButton){
           client.getView().notifyCard(new CardMessage(GodName.valueOf(playButton_JButton.getName())));
       };
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt,ArrayList<JButton> cards) {
        // TODO add your handling code here:
        for(int i = 0; i <cards.size();i++)
        {
            if(evt.getSource().equals(cards.get(i)))
            {
                client.setCard(GodName.valueOf(cards.get(i).getText()));
                client.getNetworkHandler().sendMessage(new CardMessage(GodName.valueOf(cards.get(i).getText())));
                System.out.println(GodName.valueOf(cards.get(i).getText()));
            }
        }
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */






    // Variables declaration


    // End of variables declaration
}

