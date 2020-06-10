package it.polimi.ing.sw.psp017.client.view.GraphicUserInterface;



import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.CardMessage;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.client.view.GodName;

import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

public class LobbyMessagePanel extends JPanel {


    private JPanel cardSelection_JPanel;
    private JLabel jLabel1;
    private KGradientPanel kGradientPanel1;
    private JButton playButton_JButton;
    private JPanel selectCardPanel_JPanel;
    private JPanel selectPanel_JPanel;
    private Client client;
    private JDialog popUp;
    private JPanel nicknameJPanel;
    private ArrayList<JLabel> nickLabel;




    public LobbyMessagePanel(final LobbyMessage lobbyMessage, final Client client) {

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
            temp.setToolTipText("Double click for God's description");

            if (client.getPlayerNumber() == 0) {
                client.setPlayerNumber(lobbyMessage.players.indexOf(client.getNickname()) + 1);

            }

            if(client.getPlayerNumber() != lobbyMessage.choosingPlayerNumber )
            {
                if(allPlayerIn(lobbyMessage)){
                    jLabel1.setText(lobbyMessage.players.get(lobbyMessage.choosingPlayerNumber-1) + "   is choosing his God  ");
                    jLabel1.setIcon(null);
                }
                else {
                    jLabel1.setText("  Waiting for other participants  ");
                    jLabel1.setIcon(new ImageIcon(LobbyMessagePanel.class.getClassLoader().getResource("dagger.png")));
                }
                temp.setBackground(Color.LIGHT_GRAY);
                playButton_JButton.setEnabled(false);
                temp.setEnabled(true);
                temp.setContentAreaFilled(true);
                temp.setBackground(new Color(200,80,254));
            }
            else
            {
                temp.setEnabled(true);
                jLabel1.setText(" Choose your God ");
                temp.setContentAreaFilled(true);
                temp.setBackground(Color.GREEN.darker());

            }


            cardsButton.add((temp));
            cardSelection_JPanel.add(temp);

        }
        if(lobbyMessage.chosenCards != null)
        {
            for(int i = 0; i < lobbyMessage.chosenCards.size();i++)
            {
                final JButton temp = new JButton();
                temp.setContentAreaFilled(false);
                temp.setName("  "+ lobbyMessage.chosenCards.get(i).toString()+ " ");
                temp.setIcon(GodView.getCard(lobbyMessage.chosenCards.get(i)).getIcon());
                temp.setBackground(null);
                temp.setToolTipText("Double click for God's description");
                temp.setEnabled(true);
                temp.setContentAreaFilled(true);
                temp.setVerticalTextPosition(JButton.TOP);
                temp.setHorizontalTextPosition(JButton.CENTER);
                temp.setFont(new java.awt.Font("Tahoma", 2, 36));
                temp.setBackground(Color.GRAY.darker());
                temp.setForeground(new java.awt.Color(204, 255, 255));
                temp.setText(lobbyMessage.players.get(lobbyMessage.players.size()-i-1));


                if(client.getPlayerNumber() != lobbyMessage.choosingPlayerNumber)
                {

                }
                else
                {
                    temp.setBackground(Color.RED.darker());

                }
               // cardsButton.add(temp);
                cardSelection_JPanel.add(temp);
                temp.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jButton1MouseClicked(evt,temp.getName());
                    }
                });

            }
        }
        ButtonGroup group = new ButtonGroup();

        for(final JToggleButton toggleButton : cardsButton){
            group.add(toggleButton);
            toggleButton.addMouseListener((new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() >= 2){
                        JLabel playerDescription = new JLabel();
                        playerDescription.setIcon(GodView.getCard(GodName.valueOf((toggleButton.getName()))).getIconDescription());
                        if(popUp == null){
                            popUp = new JDialog((JFrame)SwingUtilities.getRoot(kGradientPanel1));
                            popUp.add(playerDescription);
                            popUp.setResizable(false);
                            popUp.setVisible(true);
                            popUp.setSize(507,278);
                            popUp.setLocationRelativeTo(null);
                            popUp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                            popUp.addWindowListener(new WindowAdapter() {
                                @Override
                                public void windowClosing(WindowEvent e) {
                                    popUp.dispose();
                                    popUp = null;
                                }
                            });}
                        else{
                            popUp.setContentPane(playerDescription);
                            popUp.revalidate();
                            popUp.repaint();
                        }
                    }
                    else {
                        if(client.getPlayerNumber()==lobbyMessage.choosingPlayerNumber) playButton_JButton.setEnabled(true);
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
        JPanel listPlayers = new JPanel();
        JLabel infoList = new JLabel();
        infoList.setText("Players in lobby...");
        infoList.setFont(new java.awt.Font("Tahoma", 2, 36));
        infoList.setForeground(Color.WHITE);
        listPlayers.add(infoList);
        nicknameJPanel = new JPanel();
        nicknameJPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(255, 255, 255), 3));
        nicknameJPanel.setOpaque(false);
        nicknameJPanel.setLayout(new java.awt.GridLayout());

        for(int i = 0; i < lobbyMessage.players.size();i++)
        {
            JLabel tempJLabel = new JLabel();
            tempJLabel.setFont(new java.awt.Font("Tahoma", 3, 36));
            tempJLabel.setForeground(Color.WHITE);
            tempJLabel.setText(" "+lobbyMessage.players.get(i)+ " ");
            tempJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            nicknameJPanel.add(tempJLabel);

        }


        cardSelection_JPanel.add(nicknameJPanel);



        selectPanel_JPanel.setOpaque(false);

        playButton_JButton.setBackground(new java.awt.Color(0, 153, 153));
        playButton_JButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/playButton.png")));
        playButton_JButton.setToolTipText("");
        playButton_JButton.setAutoscrolls(true);
        playButton_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButton_JButtonActionPerformed(evt,lobbyMessage.choosingPlayerNumber);
            }
        });
        selectPanel_JPanel.add(playButton_JButton);
        playButton_JButton.setEnabled(false);

        GroupLayout kGradientPanel1Layout = new GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(selectCardPanel_JPanel, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                                        .addComponent(cardSelection_JPanel, GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                        .addGap(20,20,20)
                                        .addComponent(listPlayers,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(nicknameJPanel,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(selectPanel_JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

                                )

                                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(43, 43, 43)
                                .addComponent(selectCardPanel_JPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(cardSelection_JPanel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                                .addGap(20,20,20)
                                .addComponent(listPlayers,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(nicknameJPanel,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(selectPanel_JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap(43, Short.MAX_VALUE))

        );
        this.setLayout(new BorderLayout());
        this.add(kGradientPanel1, BorderLayout.CENTER);



        kGradientPanel1.backgroundGradient(10);
    }




    private void playButton_JButtonActionPerformed(ActionEvent evt, int choosingPlayerNumber) {
       if(evt.getSource() == playButton_JButton && client.getPlayerNumber()==choosingPlayerNumber){
           playButton_JButton.setEnabled(false);
           playButton_JButton.setVisible(false);
           client.setCard(GodName.valueOf(playButton_JButton.getName()));
           client.getView().notifyCard(new CardMessage(GodName.valueOf(playButton_JButton.getName())));
       };
    }

private boolean allPlayerIn(LobbyMessage lobbyMessage){
        return (lobbyMessage.availableCards.size() + lobbyMessage.chosenCards.size()) == lobbyMessage.players.size();
}

    private void jButton1MouseClicked(MouseEvent evt, String name) {

        if(evt.getClickCount() >= 2){
            JButton temp = (JButton) evt.getSource();

            JLabel playerDescription = new JLabel();
            playerDescription.setIcon(GodView.getCard(GodName.valueOf((temp.getName().replaceAll("\\s+","")))).getIconDescription());
            if(popUp == null){
                popUp = new JDialog((JFrame)SwingUtilities.getRoot(kGradientPanel1));
                popUp.add(playerDescription);
                popUp.setResizable(false);
                popUp.setVisible(true);
                popUp.setSize(507,278);
                popUp.setLocationRelativeTo(null);
                popUp.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                popUp.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        popUp.dispose();
                        popUp = null;
                    }
                });}
            else{
                popUp.setContentPane(playerDescription);
                popUp.revalidate();
                popUp.repaint();
            }
        }

    }
    /**
     * @param args the command line arguments
     */






    // Variables declaration


    // End of variables declaration
}

