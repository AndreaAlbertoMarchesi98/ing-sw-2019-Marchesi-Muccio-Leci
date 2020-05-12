package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.view.CLI;
import it.polimi.ing.sw.psp017.view.View;

import java.util.ArrayList;
import java.util.Scanner;



    public class Client {
        private  NetworkHandler networkHandler;
        private int index;
        private View view;
        private int playerNumber = 0;
        private String nickname;


        public ArrayList<PlayersInfo> playersInfo;



        public static void main(String[] args) {
            Client client = new Client();
            Scanner scanner;
            System.out.println("CLI or GUI?");
            scanner = new Scanner(System.in);
            String choice = "C";//scanner.nextLine();






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

        public void setPlayerNumber(int playerNumber) {
            System.out.println("calling set player number" + playerNumber);
            this.playerNumber = playerNumber;
        }

        public int getPlayerNumber() {
            return playerNumber;
        }









    }


