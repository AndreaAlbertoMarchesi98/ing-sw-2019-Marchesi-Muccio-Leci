package it.polimi.ing.sw.psp017.controller.client;
import it.polimi.ing.sw.psp017.view.*;

import java.util.Scanner;



    public class Client {
        private  NetworkHandler networkHandler;
        private int index;
        private View view;
        private int playerIndex;
        private String nickname;



        public static void main(String[] args) {
            Client client = new Client();
            Scanner scanner;
            System.out.println("CLI or GUI?");
            scanner = new Scanner(System.in);
            String choice = scanner.nextLine();



            if (choice.equals("C")) {
               client.view = new CLI(client);
            }
            else if(choice.equals("G")){
                //client.view = new GUI();
            }

            client.networkHandler = new NetworkHandler(client.view);
            client.networkHandler.startConnection();

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

        public void setPlayerIndex(int playerIndex) {
            this.playerIndex = playerIndex;
        }

        public int getPlayerIndex() {
            return playerIndex;
        }

    }


