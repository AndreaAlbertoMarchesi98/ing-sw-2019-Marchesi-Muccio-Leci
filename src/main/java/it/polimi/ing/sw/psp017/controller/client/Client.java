package it.polimi.ing.sw.psp017.controller.client;
import it.polimi.ing.sw.psp017.view.*;

import java.util.Scanner;



    public class Client {

        public static void main(String[] args) {
            Scanner scanner;
            System.out.println("CLI or GUI?");
            scanner = new Scanner(System.in);
            String choice = scanner.nextLine();
           // client.startConnection();

            if (choice.equals("C")) {
               new ClientCLI();
            }
            else if(choice.equals("G")){
                new GUI();
            }

        }


    }


