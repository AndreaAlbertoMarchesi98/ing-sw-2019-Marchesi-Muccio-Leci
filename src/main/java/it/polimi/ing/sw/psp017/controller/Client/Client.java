package it.polimi.ing.sw.psp017.controller.Client;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.controller.server.VirtualView;
import it.polimi.ing.sw.psp017.view.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;



    public class Client {
        private  String nickname;

        private NetworkHandler networkHandler;

        private View view;

        public Client (){
            networkHandler = new NetworkHandler(this);
        }


        public static void main(String[] args) {
            Scanner scanner;
            System.out.println("CLI or GUI?");
            scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
           // client.startConnection();

            if (choice.equals("C")) {
               new CLI();
            }
            else if(choice.equals("G")){
                new GUI();
            }



        }

        public void setView(View v){view = v;}
        public View getView(){return view;}


    }


