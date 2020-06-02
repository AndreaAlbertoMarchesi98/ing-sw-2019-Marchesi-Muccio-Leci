package it.polimi.ing.sw.psp017.view.GraphicUserInterface;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import it.polimi.ing.sw.psp017.controller.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ConnectionPanel extends JFrame {


    public  ConnectionPanel(Client client) {


        this.client = client;
        kGradientPanel1 = new KGradientPanel();
        selectCardPanel_JPanel = new JPanel();
        jLabel1 = new JLabel();
        jPanel1 = new JPanel();
        jLabel3 = new JLabel();
        IPTextField = new JTextField();
        jLabel4 = new JLabel();
        PORTTextField = new JTextField();
        okPanel = new JPanel();
        jButton1 = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(400, 550));

        kGradientPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 255), 5));
        kGradientPanel1.setkEndColor(new Color(255, 102, 102));
        kGradientPanel1.setkGradientFocus(100);
        kGradientPanel1.setLayout(new GridLayout(0, 1, 20, 20));

        selectCardPanel_JPanel.setOpaque(false);

        jLabel1.setFont(new Font("Tahoma", 2, 36)); // NOI18N
        jLabel1.setForeground(new Color(204, 255, 255));
        jLabel1.setText("Connection setting :  ");
        jLabel1.setMaximumSize(new Dimension(96, 90));
        selectCardPanel_JPanel.add(jLabel1);

        kGradientPanel1.add(selectCardPanel_JPanel);

        jPanel1.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 3));
        jPanel1.setForeground(new Color(0, 255, 255));
        jPanel1.setLayout(new GridLayout(0, 2));

        jLabel3.setBackground(new Color(0, 0, 0));
        jLabel3.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setForeground(new Color(204, 255, 255));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel3.setText("  insert ip address : ");
        jPanel1.add(jLabel3);

        IPTextField.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        IPTextField.setHorizontalAlignment(JTextField.CENTER);
        IPTextField.setText("127.0.0.1");

        IPTextField.setToolTipText("");
        jPanel1.add(IPTextField);

        jLabel4.setBackground(new Color(0, 0, 0));
        jLabel4.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setForeground(new Color(204, 255, 255));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText("  socket port :  ");
        jPanel1.add(jLabel4);

        PORTTextField.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        PORTTextField.setHorizontalAlignment(JTextField.CENTER);
        PORTTextField.setText("7778");
        jPanel1.add(PORTTextField);

        kGradientPanel1.add(jPanel1);

        okPanel.setOpaque(false);

        jButton1.setBackground(new Color(0, 153, 153));
        jButton1.setFont(new Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("start connection ");
        jButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        okPanel.add(jButton1);

        kGradientPanel1.add(okPanel);

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
        setVisible(true);
    }// </editor-fold>

    private void jButton1ActionPerformed(ActionEvent evt)  {
        try{
            client.getNetworkHandler().startConnection(IPTextField.getText(),Integer.parseInt(PORTTextField.getText()));
        }catch (IOException e){
            JOptionPane.showMessageDialog(this, "Error. No server found" , "Error" , JOptionPane.WARNING_MESSAGE);
            IPTextField.setText("127.0.0.1");
            PORTTextField.setText("7778");
        }


    }


    // Variables declaration - do not modify
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JTextField IPTextField;
    private JTextField PORTTextField;
    private KGradientPanel kGradientPanel1;
    private JPanel okPanel;
    private JPanel selectCardPanel_JPanel;
    private Client client;
    // End of variables declaration
}
