package it.polimi.ing.sw.psp017.client.view.GraphicUserInterface;


import it.polimi.ing.sw.psp017.client.Client;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.GameSetUpMessage;
import it.polimi.ing.sw.psp017.client.view.GodName;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author enci
 */
public class FirstPlayerFrame extends JFrame {

    private JLabel SelectNumberOfPlayerLabel;
    private JCheckBox ThreePlayerButton;
    private JCheckBox TwoPlayerButton;
    private JPanel buttonsPanel;
    private JButton playButtonJButton;
    private JLabel sxCardInfoJLabel;
    private JLabel dxCardIconJLabel;
    private JScrollPane jScrollPane1;
    private JSeparator jSeparator1;
    private JToggleButton[] deckJToggleButton;
    private KGradientPanel kGradientPanel1;
    private KGradientPanel kGradientPanelPlayButton;
    private ButtonGroup numberOfPlayersGroup;
    private JButton resetNumberOfPlyer;
    private JPanel selectNumberOfPlayers_JPanel;
    private JPanel viewCardsPanel;
    // End of variables declaration

    ArrayList<GodName> selectedCards = new ArrayList();
    ArrayList<GodName> cards = new ArrayList<>(EnumSet.allOf(GodName.class));
    int numberOfPlayer;
    private Client client;



    public FirstPlayerFrame(Client client) {



        this.client = client;
        numberOfPlayersGroup = new ButtonGroup();
        kGradientPanel1 = new KGradientPanel();
        kGradientPanelPlayButton = new KGradientPanel();
        playButtonJButton = new JButton();
        selectNumberOfPlayers_JPanel = new JPanel();
        SelectNumberOfPlayerLabel = new JLabel();
        ThreePlayerButton = new JCheckBox();
        TwoPlayerButton = new JCheckBox();
        resetNumberOfPlyer = new JButton();
        jScrollPane1 = new JScrollPane();
        buttonsPanel = new JPanel();
        viewCardsPanel = new JPanel();
        sxCardInfoJLabel = new JLabel();
        dxCardIconJLabel = new JLabel();
        jSeparator1 = new JSeparator();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("firsPlayer");
        setMinimumSize(new java.awt.Dimension(1200, 800));

        kGradientPanel1.setkEndColor(new java.awt.Color(255, 51, 153));
        kGradientPanel1.setkStartColor(new java.awt.Color(51, 204, 255));

        kGradientPanelPlayButton.setkEndColor(new java.awt.Color(248, 81, 30));
        kGradientPanelPlayButton.setkStartColor(new java.awt.Color(240, 252, 58));

        playButtonJButton.setBackground(new java.awt.Color(0, 153, 153));
        playButtonJButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF/playButton.png"))); // NOI18N
        playButtonJButton.setToolTipText("");
        playButtonJButton.setAutoscrolls(true);
        playButtonJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        kGradientPanelPlayButton.add(playButtonJButton);

        selectNumberOfPlayers_JPanel.setOpaque(false);

        SelectNumberOfPlayerLabel.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        SelectNumberOfPlayerLabel.setForeground(new java.awt.Color(204, 204, 204));
        SelectNumberOfPlayerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        SelectNumberOfPlayerLabel.setText("SELECT NUMBER OF PLAYERS :");
        SelectNumberOfPlayerLabel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new java.awt.Color(255, 204, 204), new java.awt.Color(204, 204, 255), new java.awt.Color(102, 102, 102), null));
        selectNumberOfPlayers_JPanel.add(SelectNumberOfPlayerLabel);

        numberOfPlayersGroup.add(ThreePlayerButton);
        ThreePlayerButton.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        ThreePlayerButton.setForeground(new java.awt.Color(102, 102, 102));
        ThreePlayerButton.setText("3");
        ThreePlayerButton.setToolTipText("");
        ThreePlayerButton.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 102))); // NOI18N
        ThreePlayerButton.setBorderPainted(true);
        ThreePlayerButton.setOpaque(false);
        ThreePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThreePlayerButtonActionPerformed(evt);
            }
        });
        selectNumberOfPlayers_JPanel.add(ThreePlayerButton);

        numberOfPlayersGroup.add(TwoPlayerButton);
        TwoPlayerButton.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        TwoPlayerButton.setForeground(new java.awt.Color(102, 102, 102));
        TwoPlayerButton.setText("2");
        TwoPlayerButton.setToolTipText("");
        TwoPlayerButton.setBorder(BorderFactory.createTitledBorder(null, "", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 102))); // NOI18N
        TwoPlayerButton.setBorderPainted(true);
        TwoPlayerButton.setOpaque(false);
        TwoPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoPlayerButtonActionPerformed(evt);
            }
        });
        selectNumberOfPlayers_JPanel.add(TwoPlayerButton);

        resetNumberOfPlyer.setIcon(new ImageIcon(getClass().getClassLoader().getResource("direction.png"))); // NOI18N
        resetNumberOfPlyer.setDoubleBuffered(true);
        resetNumberOfPlyer.setHorizontalTextPosition(SwingConstants.CENTER);
        resetNumberOfPlyer.setOpaque(false);
        resetNumberOfPlyer.setVerticalAlignment(SwingConstants.TOP);
        resetNumberOfPlyer.setVerticalTextPosition(SwingConstants.TOP);
        resetNumberOfPlyer.setVisible(false);
        resetNumberOfPlyer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetNumberOfPlyerActionPerformed(evt);
            }
        });
        selectNumberOfPlayers_JPanel.add(resetNumberOfPlyer);

        jScrollPane1.setBackground(new java.awt.Color(153, 0, 153));
        jScrollPane1.setOpaque(false);

        buttonsPanel.setBackground(new java.awt.Color(204, 204, 255));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new java.awt.GridLayout(1, 0, 5, 0));



        deckJToggleButton = new JToggleButton[cards.size()];

        for(int i = 0; i < cards.size();i++)
        {
            deckJToggleButton[i] = new JToggleButton();
            deckJToggleButton[i].setBackground(new java.awt.Color(0, 102, 102));
            deckJToggleButton[i].setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
            deckJToggleButton[i].setForeground(new java.awt.Color(204, 255, 255));
            deckJToggleButton[i].setText(cards.get(i).toString());
            deckJToggleButton[i].setOpaque(true);



            deckJToggleButton[i].setEnabled(false);


            deckJToggleButton[i].addActionListener(new java.awt.event.ActionListener() {

                public void actionPerformed(java.awt.event.ActionEvent evt) {

                    jToggleButton1ActionPerformed(evt);
                }
            });

            deckJToggleButton[i].addMouseListener(new java.awt.event.MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    deckJToggleButtonMouseEntered(evt);
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });

            buttonsPanel.add(deckJToggleButton[i]);
        }

        kGradientPanelPlayButton.backgroundGradient();

        jScrollPane1.setViewportView(buttonsPanel);

        viewCardsPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(153, 255, 255), 2));
        viewCardsPanel.setOpaque(false);
        viewCardsPanel.setLayout(new java.awt.GridLayout(1, 0, 10, 0));

        sxCardInfoJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sxCardInfoJLabel.setIcon((GodView.getCard(GodName.APOLLO).getIconDescription())); // NOI18N
        sxCardInfoJLabel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, new java.awt.Color(0, 102, 102), null, null));
        sxCardInfoJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        viewCardsPanel.add(sxCardInfoJLabel);

        dxCardIconJLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dxCardIconJLabel.setIcon(GodView.getCard(GodName.APOLLO).getIcon()); // NOI18N
        dxCardIconJLabel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new java.awt.Color(0, 204, 0), new java.awt.Color(204, 0, 204), null, null));
        dxCardIconJLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        viewCardsPanel.add(dxCardIconJLabel);

        jSeparator1.setBackground(new java.awt.Color(204, 0, 153));
        jSeparator1.setForeground(new java.awt.Color(255, 255, 255));

        GroupLayout kGradientPanel1Layout = new GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(kGradientPanelPlayButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(selectNumberOfPlayers_JPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING)
                        .addComponent(viewCardsPanel, GroupLayout.DEFAULT_SIZE, 1095, Short.MAX_VALUE)
                        .addComponent(jSeparator1)
        );
        kGradientPanel1Layout.setVerticalGroup(
                kGradientPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(selectNumberOfPlayers_JPanel, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(viewCardsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(kGradientPanelPlayButton, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(kGradientPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(kGradientPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);

        kGradientPanelPlayButton.backgroundGradient(10);
        kGradientPanel1.backgroundGradient(10);


        this.setIconImage(new ImageIcon(this.getClass().getClassLoader().getResource("logo.png")).getImage());




    }//ending initialization






    private void deckJToggleButtonMouseEntered(MouseEvent evt) {
        for(int i = 0; i < deckJToggleButton.length;i++) {

            if (evt.getSource() == deckJToggleButton[i]) {
                System.out.println("trovato ad indice " + i);
                System.out.println(deckJToggleButton[i].getName());


                dxCardIconJLabel.setIcon(GodView.getCard((cards.get(i))).getIcon());
                sxCardInfoJLabel.setIcon(GodView.getCard(cards.get(i)).getIconDescription());

            }
        }
    }



    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
        client.getNetworkHandler().sendMessage(new GameSetUpMessage(selectedCards));
        playButtonJButton.setVisible(false);
        resetNumberOfPlyer.setVisible(false);
        System.out.println(selectedCards);

    }

    private void ThreePlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {
        setEnableDeckJToggleButton(true);
        setEnableDeckJCheckBox(false);
        resetNumberOfPlyer.setVisible(true);
        numberOfPlayer = 3;
    }

    private void TwoPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {

        setEnableDeckJToggleButton(true);
        setEnableDeckJCheckBox(false);
        resetNumberOfPlyer.setVisible(true);
        numberOfPlayer = 2;


    }

    private void setEnableDeckJCheckBox(boolean isEnabled) {
        TwoPlayerButton.setEnabled(isEnabled);
        ThreePlayerButton.setEnabled(isEnabled);
    }

    private void setEnableDeckJToggleButton(boolean isEnabled) {
        for(JToggleButton button : deckJToggleButton)
        {
            button.setEnabled(isEnabled);
        }
    }

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("size :" + selectedCards.size());



      //  if(selectedGods.size()== numberOfPlayers) play_JButton.setEnabled(true);


        for(int i = 0; i < deckJToggleButton.length;i++)
        {
            if(evt.getSource()==deckJToggleButton[i])
            {
                System.out.println("trovato ad indice " + i);
                System.out.println(deckJToggleButton[i].getName());

                if(!deckJToggleButton[i].isSelected()) selectedCards.remove(cards.get(i)); //elemento gia presente

                else selectedCards.add(cards.get(i));
                System.out.println(selectedCards);
            }
        }
        if(selectedCards.size() == numberOfPlayer)
        {
            System.out.println("selectedCard size " + selectedCards.size());
            setEnableDeckJToggleButton(false);
            playButtonJButton.setEnabled(true);
        }
       // System.out.println(cards.get(i));

    }

    private void resetNumberOfPlyerActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        setEnableDeckJToggleButton(false);
        setEnableDeckJCheckBox(true);
        selectedCards.clear();
        TwoPlayerButton.setSelected(false);
        ThreePlayerButton.setSelected(false);
        for(JToggleButton button : deckJToggleButton)
        {
            button.setSelected(false);
        }
        playButtonJButton.setEnabled(false);



    }




}

