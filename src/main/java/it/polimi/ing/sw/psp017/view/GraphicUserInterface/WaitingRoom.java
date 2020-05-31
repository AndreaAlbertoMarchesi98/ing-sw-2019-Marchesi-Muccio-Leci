package it.polimi.ing.sw.psp017.view.GraphicUserInterface;



import javax.swing.*;
import java.awt.*;


    public class WaitingRoom extends JPanel {
        private JLabel infoLabel;
        private JLabel queueLabel;
        private JLabel boatBigLabel;
        private JLabel boatSmallLabel;
        private JPanel northPanel;
        private JPanel westPanel;
        private JPanel centerPanel;
        private JPanel eastPanel;
        private KGradientPanel kGradientPanel1;

        public WaitingRoom(int numPlayers) {

            kGradientPanel1 = new KGradientPanel();
            northPanel = new JPanel();
            westPanel = new JPanel();
            centerPanel = new JPanel();
            infoLabel = new JLabel();
            queueLabel = new JLabel();
            boatBigLabel = new JLabel();
            boatSmallLabel = new JLabel();
            eastPanel = new JPanel();


            kGradientPanel1.setForeground(new Color(102, 153, 0));
            kGradientPanel1.setkEndColor(new Color(204, 204, 255));
            kGradientPanel1.setkStartColor(new Color(204, 255, 255));
            kGradientPanel1.setLayout(new BorderLayout(20, 40));
            kGradientPanel1.setSize(1000,800);

            infoLabel.setFont(new Font("Tahoma", 3, 48));
            infoLabel.setForeground(new Color(0, 153, 102));
            infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            infoLabel.setText("A player is creating a new game");

            queueLabel.setFont(new Font("Tahoma", 2, 36));
            queueLabel.setForeground(new Color(0, 102, 102));
            queueLabel.setHorizontalAlignment(SwingConstants.CENTER);
            queueLabel.setText("");

            boatBigLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            boatBigLabel.setIcon(new ImageIcon("C:\\Users\\user\\IdeaProjects\\ing-sw-2019-Marchesi-Muccio-Leci\\src\\main\\java\\it\\polimi\\ing\\sw\\psp017\\view\\res\\title_boat_right.png"));

            boatSmallLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            boatSmallLabel.setIcon(new ImageIcon("C:\\Users\\user\\IdeaProjects\\ing-sw-2019-Marchesi-Muccio-Leci\\src\\main\\java\\it\\polimi\\ing\\sw\\psp017\\view\\res\\title_boat_left.png"));

            GroupLayout centerPanelLayout = new GroupLayout(centerPanel);
            centerPanel.setLayout(centerPanelLayout);
            centerPanelLayout.setHorizontalGroup(
                    centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(infoLabel, GroupLayout.DEFAULT_SIZE, 815, Short.MAX_VALUE)
                            .addGroup(GroupLayout.Alignment.CENTER, centerPanelLayout.createSequentialGroup()
                                    .addComponent(queueLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                                    .addComponent(boatSmallLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(113, 113, 113))
                            .addGroup(GroupLayout.Alignment.TRAILING, centerPanelLayout.createSequentialGroup()
                                    .addComponent(boatBigLabel, GroupLayout.PREFERRED_SIZE, 294, GroupLayout.PREFERRED_SIZE)
                                    .addGap(219, 219, 219))
            );
            centerPanelLayout.setVerticalGroup(
                    centerPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addGroup(centerPanelLayout.createSequentialGroup()
                                    .addGap(0,20,40)
                                    .addComponent(infoLabel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(queueLabel, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boatBigLabel, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(boatSmallLabel, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
                            )
            );

            eastPanel.setOpaque(false);
            northPanel.setOpaque(false);
            westPanel.setOpaque(false);
            centerPanel.setOpaque(false);

            kGradientPanel1.add(northPanel, BorderLayout.NORTH);
            kGradientPanel1.add(westPanel, BorderLayout.WEST);
            kGradientPanel1.add(eastPanel, BorderLayout.EAST);
            kGradientPanel1.add(centerPanel, BorderLayout.CENTER);

            this.setLayout(new BorderLayout());
            this.add(kGradientPanel1, BorderLayout.CENTER);
            kGradientPanel1.backgroundTransition();
            kGradientPanel1.backgroundGradient(10);


        }
    }


