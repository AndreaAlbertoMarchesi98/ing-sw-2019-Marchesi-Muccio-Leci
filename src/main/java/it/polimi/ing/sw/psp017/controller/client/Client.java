package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.GUI;
import it.polimi.ing.sw.psp017.view.GodName;
import it.polimi.ing.sw.psp017.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;



    public class Client {
        private  NetworkHandler networkHandler;
        private int index;
        private View view;
        private int playerNumber = 0;
        private String nickname;
        private GodName card;

        public void setCard(GodName card){
            this.card = card;
        }
        public GodName getCard(){
            return card;
        }
        public ArrayList<PlayersInfo> playersInfo;



        public static void main(String[] args) throws IOException {
            Client client = new Client();
           // Scanner scanner;
            //System.out.println("CLI or GUI?");
          //  scanner = new Scanner(System.in);
           // String choice = "G";//scanner.nextLine();



         /*   if (choice.equals("C")) {
               client.view = new CLI(client);
               public static void main(String args[]) {
        /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
             * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
             */
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */

            /*}
            else if(choice.equals("G")){*/
            client.view = new GUI(client);
           // }

            client.networkHandler = new NetworkHandler(client.view);
         /*   if(choice.equals("C")){
                client.networkHandler.startConnection();

            }*/


        }

        public NetworkHandler getNetworkHandler(){
            return this.networkHandler;
        }


        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getNickname() {
            return nickname;
        }

        public void setPlayerNumber(int playerNumber) {
            System.out.println("calling set player number" + playerNumber);
            this.playerNumber = playerNumber;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }

        public void setView(View view){
            this.view = view;
            networkHandler.setView(view);
        }

        public View getView(){
            return view;
        }


    }


