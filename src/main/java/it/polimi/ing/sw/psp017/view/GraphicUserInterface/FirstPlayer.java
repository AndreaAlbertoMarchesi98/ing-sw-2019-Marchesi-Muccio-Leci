package it.polimi.ing.sw.psp017.view.GraphicUserInterface;


import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.GameSetUpMessage;
import it.polimi.ing.sw.psp017.view.GUI;
import it.polimi.ing.sw.psp017.view.GodName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;

public class FirstPlayer extends JFrame {

    private int numberOfPlayers = 0;
    private ArrayList<JCheckBox> checkBoxArray;
    private ArrayList<JCheckBox> selectedGods;
    private ArrayList<GodName> CardsMessage;
    private Client client;



    private JButton APOLLO_JButton;
    private JCheckBox APOLLO_JCheckBox;
    private JButton ARTEMIS_JButton;
    private JCheckBox ARTEMIS_JCheckBox;
    private JButton ATHENA_JButton;
    private JCheckBox ATHENA_JCheckBox;
    private JButton ATLAS_JButton;
    private JCheckBox ATLAS_JCheckBox;
    private ButtonGroup CardsButtonPlayers;
    private JButton DEMETER_JButton;
    private JCheckBox DEMETER_JCheckBox;
    private JLabel downCard_JLabel;
    private JButton HEPHAESTUS_JButton;
    private JCheckBox HEPHAESTUS_JCheckBox;
    private JButton MINOTAUR_JButton;
    private JCheckBox MINOTAUR_JCheckBox;
    private JButton PAN_JButton;
    private JCheckBox PAN_JCheckBox;
    private JButton PROMETHEUS_JButton;
    private JCheckBox PROMETHEUS_JCheckBox;
    private JLabel SelectNumberOfPlayerLabel;
    private JLabel SelectNumberOfPlayerLabel1;
    private JCheckBox ThreePlayerButton;
    private JCheckBox TwoPlayerButton;
    private JPanel chooseCardPanel;
    private JPanel godsSelectionPanel_JPanel;
    private JButton play_JButton;
    private JSeparator jSeparator2;
    private KGradientPanel kGradientPanel1;
    private KGradientPanel kGradientPanel3;
    private JPanel lowerPanel_JPanel;
    private JPanel rightPanel_JPanel;
    private ButtonGroup selectNumberOfPlayersGroup;
    private JPanel selectNumberOfPlayers_JPanel;
    private JLabel upperCardJLabel;
    private  Dimension dim;




    public  FirstPlayer(Client client) {

        this.client = client;
        selectNumberOfPlayersGroup = new ButtonGroup();
        CardsButtonPlayers = new ButtonGroup();
        kGradientPanel1 = new KGradientPanel();
        selectNumberOfPlayers_JPanel = new JPanel();
        SelectNumberOfPlayerLabel = new JLabel();
        ThreePlayerButton = new JCheckBox();
        TwoPlayerButton = new JCheckBox();
        jSeparator2 = new JSeparator();
        chooseCardPanel = new JPanel();
        SelectNumberOfPlayerLabel1 = new JLabel();
        kGradientPanel3 = new KGradientPanel();
        play_JButton = new JButton();
        lowerPanel_JPanel = new JPanel();
        godsSelectionPanel_JPanel = new JPanel();
        APOLLO_JButton = new JButton();
        APOLLO_JCheckBox = new JCheckBox();
        ARTEMIS_JButton = new JButton();
        ARTEMIS_JCheckBox = new JCheckBox();
        ATHENA_JButton = new JButton();
        ATHENA_JCheckBox = new JCheckBox();
        ATLAS_JButton = new JButton();
        ATLAS_JCheckBox = new JCheckBox();
        DEMETER_JButton = new JButton();
        DEMETER_JCheckBox = new JCheckBox();
        HEPHAESTUS_JButton = new JButton();
        HEPHAESTUS_JCheckBox = new JCheckBox();
        MINOTAUR_JButton = new JButton();
        MINOTAUR_JCheckBox = new JCheckBox();
        PAN_JButton = new JButton();
        PAN_JCheckBox = new JCheckBox();
        PROMETHEUS_JButton = new JButton();
        PROMETHEUS_JCheckBox = new JCheckBox();
        rightPanel_JPanel = new JPanel();
        upperCardJLabel = new JLabel();
        downCard_JLabel = new JLabel();


    /*    setBackground(new java.awt.Color(0, 204, 204));
        setBounds(new java.awt.Rectangle(1280, 720, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setFont(new java.awt.Font("Gentium Book Basic", 2, 10));
        setForeground(java.awt.Color.cyan);
        setMinimumSize(new java.awt.Dimension(600, 325));*/

        kGradientPanel1.setForeground(new java.awt.Color(153, 153, 153));
        kGradientPanel1.setkEndColor(new java.awt.Color(0, 153, 153));
        kGradientPanel1.setkStartColor(new java.awt.Color(102, 0, 102));

        selectNumberOfPlayers_JPanel.setOpaque(false);

        SelectNumberOfPlayerLabel.setFont(new java.awt.Font("Dialog", 1, 24));
        SelectNumberOfPlayerLabel.setForeground(new java.awt.Color(204, 204, 204));
        SelectNumberOfPlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        SelectNumberOfPlayerLabel.setText("SELECT NUMBER OF PLAYERS :");
        SelectNumberOfPlayerLabel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 153), new java.awt.Color(153, 153, 255), new java.awt.Color(204, 0, 51), new java.awt.Color(0, 153, 153)));
        selectNumberOfPlayers_JPanel.add(SelectNumberOfPlayerLabel);

        selectNumberOfPlayersGroup.add(ThreePlayerButton);
        ThreePlayerButton.setFont(new java.awt.Font("Dialog", 1, 48));
        ThreePlayerButton.setForeground(new java.awt.Color(102, 102, 102));
        ThreePlayerButton.setText("3");
        ThreePlayerButton.setToolTipText("");
        ThreePlayerButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 102)));
        ThreePlayerButton.setBorderPainted(true);
        ThreePlayerButton.setOpaque(false);
        ThreePlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThreePlayerButtonActionPerformed(evt);
            }
        });
        selectNumberOfPlayers_JPanel.add(ThreePlayerButton);

        selectNumberOfPlayersGroup.add(TwoPlayerButton);
        TwoPlayerButton.setFont(new java.awt.Font("Dialog", 1, 48));
        TwoPlayerButton.setForeground(new java.awt.Color(102, 102, 102));
        //TwoPlayerButton.setSelected(true);
        TwoPlayerButton.setText("2");
        TwoPlayerButton.setToolTipText("");
        TwoPlayerButton.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18), new java.awt.Color(102, 0, 102)));
        TwoPlayerButton.setBorderPainted(true);
        TwoPlayerButton.setOpaque(false);
        TwoPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TwoPlayerButtonActionPerformed(evt);
            }
        });
        selectNumberOfPlayers_JPanel.add(TwoPlayerButton);

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setFont(new java.awt.Font("Tahoma", 0, 24));

        chooseCardPanel.setOpaque(false);

        SelectNumberOfPlayerLabel1.setFont(new java.awt.Font("Dialog", 1, 24));
        SelectNumberOfPlayerLabel1.setForeground(new java.awt.Color(204, 204, 204));
        SelectNumberOfPlayerLabel1.setText("CHOOSE CARDS :");
        SelectNumberOfPlayerLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        SelectNumberOfPlayerLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(0, 0, 153), new java.awt.Color(153, 153, 255), new java.awt.Color(204, 0, 51), new java.awt.Color(0, 153, 153)));
        chooseCardPanel.add(SelectNumberOfPlayerLabel1);

        kGradientPanel3.setkEndColor(new java.awt.Color(0, 153, 153));
        kGradientPanel3.setkStartColor(new java.awt.Color(0, 204, 204));

        play_JButton.setBackground(new java.awt.Color(0, 153, 153));
        play_JButton.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("STUFF/playButton.png")));
        play_JButton.setToolTipText("");
        play_JButton.setAutoscrolls(true);
        play_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });
        play_JButton.setEnabled(false);
        kGradientPanel3.add(play_JButton);

        lowerPanel_JPanel.setOpaque(false);

        godsSelectionPanel_JPanel.setOpaque(false);
        godsSelectionPanel_JPanel.setLayout(new java.awt.GridLayout(9, 2, 10, 0));



        APOLLO_JButton.setBackground(new java.awt.Color(0, 51, 102));
        APOLLO_JButton.setFont(new java.awt.Font("Source Code Pro Black", 2, 11));
        APOLLO_JButton.setForeground(new java.awt.Color(102, 255, 255));
        APOLLO_JButton.setText("APOLLO");
        APOLLO_JButton.setMultiClickThreshhold(3L);

        APOLLO_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                APOLLO_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(APOLLO_JButton);


        checkBoxArray = new ArrayList<>();
        selectedGods = new ArrayList<>();
        CardsMessage = new ArrayList<>();

        APOLLO_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                APOLLO_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(APOLLO_JCheckBox);
        godsSelectionPanel_JPanel.add(APOLLO_JCheckBox);



        ARTEMIS_JButton.setBackground(new java.awt.Color(0, 51, 102));
        ARTEMIS_JButton.setForeground(new java.awt.Color(102, 255, 255));
        ARTEMIS_JButton.setText("ARTEMIS");
        ARTEMIS_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ARTEMIS_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(ARTEMIS_JButton);
        ARTEMIS_JCheckBox.setOpaque(true);

        ARTEMIS_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ARTEMIS_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(ARTEMIS_JCheckBox);
        godsSelectionPanel_JPanel.add(ARTEMIS_JCheckBox);

        ATHENA_JButton.setBackground(new java.awt.Color(0, 51, 102));
        ATHENA_JButton.setForeground(new java.awt.Color(102, 255, 255));
        ATHENA_JButton.setText("ATHENA");
        ATHENA_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATHENA_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(ATHENA_JButton);

        //CardsButtonPlayers.add(ATHENA_JCheckBox);
        ATHENA_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATHENA_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(ATHENA_JCheckBox);
        godsSelectionPanel_JPanel.add(ATHENA_JCheckBox);

        ATLAS_JButton.setBackground(new java.awt.Color(0, 51, 102));
        ATLAS_JButton.setForeground(new java.awt.Color(102, 255, 255));
        ATLAS_JButton.setText("ATLAS");
        ATLAS_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATLAS_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(ATLAS_JButton);

        ATLAS_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ATLAS_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(ATLAS_JCheckBox);
        godsSelectionPanel_JPanel.add(ATLAS_JCheckBox);

        DEMETER_JButton.setBackground(new java.awt.Color(0, 51, 102));
        DEMETER_JButton.setForeground(new java.awt.Color(102, 255, 255));
        DEMETER_JButton.setText("DEMETER");
        DEMETER_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEMETER_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(DEMETER_JButton);

        DEMETER_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEMETER_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(DEMETER_JCheckBox);
        godsSelectionPanel_JPanel.add(DEMETER_JCheckBox);

        HEPHAESTUS_JButton.setBackground(new java.awt.Color(0, 51, 102));
        HEPHAESTUS_JButton.setForeground(new java.awt.Color(102, 255, 255));
        HEPHAESTUS_JButton.setText("HEPHAESTUS");
        HEPHAESTUS_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HEPHAESTUS_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(HEPHAESTUS_JButton);

        HEPHAESTUS_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HEPHAESTUS_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(HEPHAESTUS_JCheckBox);
        godsSelectionPanel_JPanel.add(HEPHAESTUS_JCheckBox);

        MINOTAUR_JButton.setBackground(new java.awt.Color(0, 51, 102));
        MINOTAUR_JButton.setForeground(new java.awt.Color(102, 255, 255));
        MINOTAUR_JButton.setText("MINOTAUR");
        MINOTAUR_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MINOTAUR_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(MINOTAUR_JButton);

        MINOTAUR_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MINOTAUR_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(MINOTAUR_JCheckBox);
        godsSelectionPanel_JPanel.add(MINOTAUR_JCheckBox);

        PAN_JButton.setBackground(new java.awt.Color(0, 51, 102));
        PAN_JButton.setForeground(new java.awt.Color(102, 255, 255));
        PAN_JButton.setText("PAN");
        PAN_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAN_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(PAN_JButton);

        //CardsButtonPlayers.add(PAN_JCheckBox);
        PAN_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PAN_JCheckBoxActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(PAN_JCheckBox);
        checkBoxArray.add(PAN_JCheckBox);

        PROMETHEUS_JButton.setBackground(new java.awt.Color(0, 51, 102));
        PROMETHEUS_JButton.setForeground(new java.awt.Color(102, 255, 255));
        PROMETHEUS_JButton.setText("PROMETHEUS");
        PROMETHEUS_JButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PROMETHEUS_JButtonActionPerformed(evt);
            }
        });
        godsSelectionPanel_JPanel.add(PROMETHEUS_JButton);

        //CardsButtonPlayers.add(PROMETHEUS_JCheckBox);
        PROMETHEUS_JCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PROMETHEUS_JCheckBoxActionPerformed(evt);
            }
        });
        checkBoxArray.add(PROMETHEUS_JCheckBox);
        godsSelectionPanel_JPanel.add(PROMETHEUS_JCheckBox);

        rightPanel_JPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        rightPanel_JPanel.setForeground(new java.awt.Color(0, 153, 153));
        rightPanel_JPanel.setOpaque(false);
        rightPanel_JPanel.setLayout(new java.awt.GridLayout(1, 2,1,0));

        upperCardJLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("APOLLO/podium-characters-Apolo.png")));
        rightPanel_JPanel.add(upperCardJLabel);

        downCard_JLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);


        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("APOLLO/APOLLO-1.png")));


        downCard_JLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rightPanel_JPanel.add(downCard_JLabel);

        javax.swing.GroupLayout lowerPanel_JPanelLayout = new javax.swing.GroupLayout(lowerPanel_JPanel);
        lowerPanel_JPanel.setLayout(lowerPanel_JPanelLayout);
        lowerPanel_JPanelLayout.setHorizontalGroup(
                lowerPanel_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lowerPanel_JPanelLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(godsSelectionPanel_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(rightPanel_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        lowerPanel_JPanelLayout.setVerticalGroup(
                lowerPanel_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(lowerPanel_JPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(lowerPanel_JPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(godsSelectionPanel_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(rightPanel_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
                kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lowerPanel_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                                .addGroup(kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jSeparator2)
                                                        .addComponent(chooseCardPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(kGradientPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                                .addComponent(selectNumberOfPlayers_JPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGap(21, 21, 21))))
        );
        kGradientPanel1Layout.setVerticalGroup(
                kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(kGradientPanel1Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(selectNumberOfPlayers_JPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(chooseCardPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lowerPanel_JPanel, GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kGradientPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap()
                        )
        );




        /*IL PANEL COL GRADIENTE DEVE ESSERE AGGIUNTO AL PANEL FIRSTPLAYER... */
        this.add(kGradientPanel1, BorderLayout.CENTER);
        //this.setPreferredSize(kGradientPanel1.getSize());
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(dim.width/2,dim.height/2);
        //this.setMinimumSize(dim/2);
        //setLocationRelativeTo(null);
    }



    private void ThreePlayerButtonActionPerformed(ActionEvent evt) {
        numberOfPlayers = 3;
        //cardManager();

    }

    private void cardManager(GodName godName)
    {

        System.out.println("size :" + selectedGods.size());

        if(!CardsMessage.contains(godName))
        {
            System.out.println(godName);
            CardsMessage.add(godName);

        }
        play_JButton.setEnabled(selectedGods.size() == numberOfPlayers);

        if(selectedGods.size() > numberOfPlayers)
        {
            for (JCheckBox selectedGod : selectedGods) {
                selectedGod.setSelected(false);
            }
            CardsMessage.clear();
            selectedGods.clear();

        }

        if(selectedGods.size()== numberOfPlayers) play_JButton.setEnabled(true);



        /*
        while(selectedGods.size() > numberOfPlayers)
        {

            CardsMessage.remove(0);
            selectedGods.get(0).setSelected(false);
            selectedGods.remove(0);
            play_JButton.setEnabled(true);
        }
         */

    }

    private void TwoPlayerButtonActionPerformed(ActionEvent evt) {

        if(numberOfPlayers != 2)
        {
            selectedGods.get(0).setSelected(false);
            selectedGods.remove(0);
            CardsMessage.remove(0);
        }
        numberOfPlayers = 2;

        //cardManager();
    }

    private void DEMETER_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\DEMETER\\podium-characters-Demeter.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\DEMETER\\DEMETER-1.png")));




    }

    private void playButtonActionPerformed(ActionEvent evt) {

        System.out.println(CardsMessage.toString());
        client.getNetworkHandler().sendMessage(new GameSetUpMessage(CardsMessage));
        play_JButton.setEnabled(false);




    }



    private void APOLLO_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\APOLLO\\podium-characters-Apolo.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\APOLLO\\APOLLO-1.png")));



    }

    private void ARTEMIS_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ARTEMIS\\podium-characters-Artemis.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ARTEMIS\\ARTEMIS-1.png")));
    }

    private void APOLLO_JCheckBoxActionPerformed(ActionEvent evt) {

        selectedGods.add(APOLLO_JCheckBox);


        cardManager(GodName.APOLLO);

    }

    private void ARTEMIS_JCheckBoxActionPerformed(ActionEvent evt) {
        selectedGods.add(ARTEMIS_JCheckBox);
        cardManager(GodName.ARTEMIS);


    }

    private void ATHENA_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ATHENA\\podium-characters-Athena.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ATHENA\\ATHENA-1.png")));
    }

    private void ATHENA_JCheckBoxActionPerformed(ActionEvent evt) {

        selectedGods.add(ATHENA_JCheckBox);
        cardManager(GodName.ATHENA);
    }

    private void ATLAS_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ATLAS\\podium-characters-Atlas.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\ATLAS\\ATLAS-1.png")));

    }

    private void ATLAS_JCheckBoxActionPerformed(ActionEvent evt) {
        selectedGods.add(ATLAS_JCheckBox);
        cardManager(GodName.ATLAS);
    }

    private void DEMETER_JCheckBoxActionPerformed(ActionEvent evt) {
        selectedGods.add(DEMETER_JCheckBox);
        cardManager(GodName.DEMETER);
    }

    private void HEPHAESTUS_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\HEPHAESTUS\\podium-characters-Hephaestus.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\HEPHAESTUS\\HEPHAESTUS-1.png")));
    }

    private void HEPHAESTUS_JCheckBoxActionPerformed(ActionEvent evt) {
        selectedGods.add(HEPHAESTUS_JCheckBox);
        cardManager(GodName.HEPHAESTUS);
    }

    private void MINOTAUR_JButtonActionPerformed(ActionEvent evt) {
        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\MINOTAUR\\podium-characters-Minotaur.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\MINOTAUR\\MINOTAUR-1.png")));
    }

    private void MINOTAUR_JCheckBoxActionPerformed(ActionEvent evt) {

        selectedGods.add(MINOTAUR_JCheckBox);
        cardManager(GodName.MINOTAUR);
    }

    private void PAN_JButtonActionPerformed(ActionEvent evt) {

        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\PAN\\podium-characters-Pan.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\PAN\\PAN-1.png")));
    }

    private void PAN_JCheckBoxActionPerformed(ActionEvent evt) {

        selectedGods.add(PAN_JCheckBox);
        cardManager(GodName.PAN);
    }

    private void PROMETHEUS_JButtonActionPerformed(ActionEvent evt) {

        upperCardJLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\PROMETHEUS\\podium-characters-Prometheus.png")));
        downCard_JLabel.setIcon(new javax.swing.ImageIcon(getClass().getClassLoader().getResource("\\PROMETHEUS\\PROMETHEUS-1.png")));
    }

    private void PROMETHEUS_JCheckBoxActionPerformed(ActionEvent evt) {

        selectedGods.add(PROMETHEUS_JCheckBox);
        cardManager(GodName.PROMETHEUS);
    }


}
