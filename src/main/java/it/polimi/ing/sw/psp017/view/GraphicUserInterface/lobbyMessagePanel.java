package it.polimi.ing.sw.psp017.view.GraphicUserInterface;


import javax.swing.*;
import java.awt.*;

public class lobbyMessagePanel extends JPanel {


    public lobbyMessagePanel() {
        initComponents();
    }



    private void initComponents() {

        kGradientPanel1 = new KGradientPanel();
        selectCardPanel_JPanel = new JPanel();
        jLabel1 = new JLabel();
        cardSelection_JPanel = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        selectPanel_JPanel = new JPanel();
        playButton_JButton = new JButton();


        kGradientPanel1.setkEndColor(new java.awt.Color(255, 102, 102));
        kGradientPanel1.setSize(1000,800);
        selectCardPanel_JPanel.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(204, 255, 255));
        jLabel1.setText("select a card  ");
        selectCardPanel_JPanel.add(jLabel1);

        cardSelection_JPanel.setOpaque(false);
        cardSelection_JPanel.setLayout(new java.awt.GridLayout(1, 0));

        jButton1.setText("jButton1");
        jButton1.setMaximumSize(new java.awt.Dimension(50, 100));
        jButton1.setMinimumSize(new java.awt.Dimension(50, 100));
        jButton1.setPreferredSize(new java.awt.Dimension(100, 200));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        cardSelection_JPanel.add(jButton1);

        jButton2.setText("jButton2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        cardSelection_JPanel.add(jButton2);

        jButton3.setText("jButton3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        cardSelection_JPanel.add(jButton3);

        selectPanel_JPanel.setOpaque(false);

        playButton_JButton.setBackground(new java.awt.Color(0, 153, 153));
        playButton_JButton.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\playButton.png")));
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

    private void playButton_JButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(lobbyMessagePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(lobbyMessagePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(lobbyMessagePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(lobbyMessagePanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }




        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new lobbyMessagePanel().setVisible(true);
            }
        });
    }

    // Variables declaration
    private JPanel cardSelection_JPanel;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel jLabel1;
    private KGradientPanel kGradientPanel1;
    private JButton playButton_JButton;
    private JPanel selectCardPanel_JPanel;
    private JPanel selectPanel_JPanel;
    // End of variables declaration
}

