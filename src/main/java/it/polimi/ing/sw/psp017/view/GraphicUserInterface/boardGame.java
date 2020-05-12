package it.polimi.ing.sw.psp017.view.GraphicUserInterface;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class boardGame extends JFrame {

    /**
     * Creates new form boardGame
     */
    public boardGame() {
        initComponents();
    }


    private void initComponents() {

        kGradientPanel1 = new KGradientPanel();
        northPanel_JPanel = new JPanel();
        boardGamePanel = new JPanel(){

            public void paintComponent(Graphics g) {

                super.paintComponent(g);

                Image img = new ImageIcon(getClass().getClassLoader().getResource("STUFF\\BOARDgAMEbACKgROUND.png")).getImage().getScaledInstance(this.getWidth(),this.getHeight(),java.awt.Image.SCALE_SMOOTH);

                Graphics2D g2d = (Graphics2D) g;
                int x = (this.getWidth() - img.getWidth(null)) / 2;
                int y = (this.getHeight() - img.getHeight(null)) / 2;
                g2d.drawImage(img, x, y, null);

                // g.drawImage(img, 0, 0, null);
            }

        };
        jLayeredPane2 = new JLayeredPane();
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton6 = new JButton();
        jButton5 = new JButton();
        jButton7 = new JButton();
        jButton8 = new JButton();
        jButton9 = new JButton();
        jButton10 = new JButton();
        jButton11 = new JButton();
        jButton12 = new JButton();
        jButton13 = new JButton();
        jButton14 = new JButton();
        jButton15 = new JButton();
        jButton16 = new JButton();
        jButton17 = new JButton();
        jButton18 = new JButton();
        jButton19 = new JButton();
        jButton20 = new JButton();
        jButton21 = new JButton();
        jButton22 = new JButton();
        jButton23 = new JButton();
        jButton24 = new JButton();
        jLayeredPane1 = new JLayeredPane();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        jLabel11 = new JLabel();
        jLabel12 = new JLabel();
        jLabel13 = new JLabel();
        jLabel14 = new JLabel();
        jLabel15 = new JLabel();
        jLabel16 = new JLabel();
        jLabel17 = new JLabel();
        jLabel18 = new JLabel();
        jLabel19 = new JLabel();
        jLabel20 = new JLabel();
        jLabel21 = new JLabel();
        jLabel22 = new JLabel();
        jLabel23 = new JLabel();
        jLabel24 = new JLabel();
        jLabel25 = new JLabel();
        jLabel26 = new JLabel();
        DXPanel = new JPanel();
        SXPanel = new JPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        kGradientPanel1.setLayout(new BorderLayout());

        northPanel_JPanel.setOpaque(false);
        northPanel_JPanel.setPreferredSize(new Dimension(1120, 200));

        GroupLayout northPanel_JPanelLayout = new GroupLayout(northPanel_JPanel);
        northPanel_JPanel.setLayout(northPanel_JPanelLayout);
        northPanel_JPanelLayout.setHorizontalGroup(
                northPanel_JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 1120, Short.MAX_VALUE)
        );
        northPanel_JPanelLayout.setVerticalGroup(
                northPanel_JPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 200, Short.MAX_VALUE)
        );

        kGradientPanel1.add(northPanel_JPanel, BorderLayout.NORTH);

        boardGamePanel.setOpaque(false);
        boardGamePanel.setPreferredSize(new Dimension(1120, 500));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new GridLayout(5, 5, 2, 2));

        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\multimedia.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel1);
        prova = new JButton("prova");
        prova.setContentAreaFilled(false);
        jLabel1.setLayout(new GridLayout());
        //jLabel1.add(new JButton("provaaaa"));
        jLabel1.add(prova);

        jButton1.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\peopleBIG.png"))); // NOI18N
        jButton1.setText("JButton1");
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setBackground(new Color(255, 204, 0));
        //jButton2.setIcon(new ImageIcon(getClass().getResource("/IMG/laptop (1).png"))); // NOI18N
        jButton2.setToolTipText("");
        jButton2.setAutoscrolls(true);
        jPanel1.add(jButton2);

        jButton3.setText("jButton3");
        jPanel1.add(jButton3);

        jButton4.setText("jButton4");
        jPanel1.add(jButton4);

        jButton6.setText("jButton6");
        jPanel1.add(jButton6);

        jButton5.setText("jButton5");
        jPanel1.add(jButton5);

        jButton7.setText("jButton7");
        jPanel1.add(jButton7);

        jButton8.setText("jButton1");
        jPanel1.add(jButton8);

        jButton9.setText("jButton2");
        jPanel1.add(jButton9);

        jButton10.setText("jButton3");
        jPanel1.add(jButton10);

        jButton11.setText("jButton4");
        jPanel1.add(jButton11);

        jButton12.setText("jButton6");
        jButton12.setContentAreaFilled(false);
        jPanel1.add(jButton12);

        jButton13.setText("jButton5");
        jPanel1.add(jButton13);

        jButton14.setText("jButton7");
        jPanel1.add(jButton14);

        jButton15.setText("jButton1");
        jPanel1.add(jButton15);

        jButton16.setText("jButton2");
        jPanel1.add(jButton16);

        jButton17.setText("jButton3");
        jPanel1.add(jButton17);

        jButton18.setText("jButton4");
        jPanel1.add(jButton18);

        jButton19.setText("jButton6");
        jPanel1.add(jButton19);

        jButton20.setText("jButton5");
        jPanel1.add(jButton20);

        jButton21.setText("jButton7");
        jPanel1.add(jButton21);

        jButton22.setText("jButton22");
        jPanel1.add(jButton22);

        jButton23.setText("jButton23");
        jPanel1.add(jButton23);

        jButton24.setText("jButton24");
        jPanel1.add(jButton24);

        jLayeredPane2.setLayer(jPanel1, JLayeredPane.DEFAULT_LAYER);

        GroupLayout jLayeredPane2Layout = new GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
                jLayeredPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 720, Short.MAX_VALUE)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                        .addContainerGap()))
        );
        jLayeredPane2Layout.setVerticalGroup(
                jLayeredPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 524, Short.MAX_VALUE)
                        .addGroup(jLayeredPane2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                                        .addContainerGap()))
        );

        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.GridLayout(5, 5, 2, 2));

        jLabel2.setText("jLabel2");
        jLabel2.setOpaque(true);
        jPanel2.add(jLabel2);

        jLabel3.setBackground(new java.awt.Color(102, 255, 255));
        jLabel3.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\peopleBIG.png"))); // NOI18N
        jLabel3.setLabelFor(boardGamePanel);
        jLabel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel3.setOpaque(true);
        jPanel2.add(jLabel3);

        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\peopleBIG.png"))); // NOI18N
        jPanel2.add(jLabel4);

        jLabel5.setText("jLabel5");
        jPanel2.add(jLabel5);

        jLabel6.setText("jLabel6");
        jPanel2.add(jLabel6);

        jLabel7.setText("jLabel2");
        jPanel2.add(jLabel7);

        jLabel8.setText("jLabel3");
        jPanel2.add(jLabel8);

        jLabel9.setText("jLabel4");
        jPanel2.add(jLabel9);

        jLabel10.setText("jLabel5");
        jPanel2.add(jLabel10);

        jLabel11.setText("jLabel6");
        jPanel2.add(jLabel11);

        jLabel12.setText("jLabel2");
        jPanel2.add(jLabel12);

        jLabel13.setText("jLabel3");
        jPanel2.add(jLabel13);

        jLabel14.setIcon(new ImageIcon(getClass().getClassLoader().getResource("STUFF\\peopleBIG.png"))); // NOI18N
        jLabel14.setText("jLabel4");
        jLabel14.setHorizontalTextPosition(SwingConstants.CENTER);
        jPanel2.add(jLabel14);

        jLabel15.setText("jLabel5");
        jPanel2.add(jLabel15);

        jLabel16.setText("jLabel6");
        jPanel2.add(jLabel16);

        jLabel17.setText("jLabel2");
        jPanel2.add(jLabel17);

        jLabel18.setText("jLabel3");
        jPanel2.add(jLabel18);

        jLabel19.setText("jLabel4");
        jPanel2.add(jLabel19);

        jLabel20.setText("jLabel5");
        jPanel2.add(jLabel20);

        jLabel21.setText("jLabel6");
        jPanel2.add(jLabel21);

        jLabel22.setText("jLabel2");
        jPanel2.add(jLabel22);

        jLabel23.setText("jLabel3");
        jPanel2.add(jLabel23);

        jLabel24.setText("jLabel4");
        jPanel2.add(jLabel24);

        jLabel25.setText("jLabel5");
        jPanel2.add(jLabel25);

        jLabel26.setText("jLabel6");
        jPanel2.add(jLabel26);

        jLayeredPane1.setLayer(jPanel2, JLayeredPane.DEFAULT_LAYER);

        GroupLayout jLayeredPane1Layout = new GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
                jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
                                .addContainerGap())
        );
        jLayeredPane1Layout.setVerticalGroup(
                jLayeredPane1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jLayeredPane1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                                .addContainerGap())
        );

        GroupLayout boardGamePanelLayout = new GroupLayout(boardGamePanel);
        boardGamePanel.setLayout(boardGamePanelLayout);
        boardGamePanelLayout.setHorizontalGroup(
                boardGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1, GroupLayout.Alignment.TRAILING)
                        .addGroup(boardGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2, GroupLayout.Alignment.TRAILING))
        );
        boardGamePanelLayout.setVerticalGroup(
                boardGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jLayeredPane1, GroupLayout.Alignment.TRAILING)
                        .addGroup(boardGamePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLayeredPane2, GroupLayout.Alignment.TRAILING))
        );

        kGradientPanel1.add(boardGamePanel, java.awt.BorderLayout.CENTER);

        DXPanel.setOpaque(false);
        DXPanel.setPreferredSize(new java.awt.Dimension(200, 524));

        GroupLayout DXPanelLayout = new GroupLayout(DXPanel);
        DXPanel.setLayout(DXPanelLayout);
        DXPanelLayout.setHorizontalGroup(
                DXPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 200, Short.MAX_VALUE)
        );
        DXPanelLayout.setVerticalGroup(
                DXPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 524, Short.MAX_VALUE)
        );

        kGradientPanel1.add(DXPanel, java.awt.BorderLayout.EAST);

        SXPanel.setOpaque(false);
        SXPanel.setPreferredSize(new java.awt.Dimension(200, 524));

        GroupLayout SXPanelLayout = new GroupLayout(SXPanel);
        SXPanel.setLayout(SXPanelLayout);
        SXPanelLayout.setHorizontalGroup(
                SXPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 200, Short.MAX_VALUE)
        );
        SXPanelLayout.setVerticalGroup(
                SXPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGap(0, 524, Short.MAX_VALUE)
        );

        kGradientPanel1.add(SXPanel, java.awt.BorderLayout.WEST);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(kGradientPanel1, GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(kGradientPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        System.out.print("provaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        System.out.print("provaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(boardGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new boardGame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private JPanel DXPanel;
    private JPanel SXPanel;
    private JPanel boardGamePanel;
    private JButton jButton1;
    private JButton jButton10;
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton13;
    private JButton jButton14;
    private JButton jButton15;
    private JButton jButton16;
    private JButton jButton17;
    private JButton jButton18;
    private JButton jButton19;
    private JButton jButton2;
    private JButton jButton20;
    private JButton jButton21;
    private JButton jButton22;
    private JButton jButton23;
    private JButton jButton24;
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel17;
    private JLabel jLabel18;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel24;
    private JLabel jLabel25;
    private JLabel jLabel26;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JLayeredPane jLayeredPane1;
    private JLayeredPane jLayeredPane2;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private KGradientPanel kGradientPanel1;
    private JPanel northPanel_JPanel;
    // End of variables declaration

    private JButton prova;
}



