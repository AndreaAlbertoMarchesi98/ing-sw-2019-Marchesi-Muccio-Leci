package it.polimi.ing.sw.psp017.controller.Client;
import it.polimi.ing.sw.psp017.controller.Client.Client;
import it.polimi.ing.sw.psp017.controller.Client.clientTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class HandlerMessageClient implements Runnable {
    Client client;
    Socket server; //DEVE ESSERE SERVER O SOCKET?
    ObjectOutputStream output;
    ObjectInputStream input;
    HandlerMessageClient(Client client, Socket server){
        this.client = client;
        this.server = server;
    }

    @Override
    public void run() {
        while(!client.isDisconnected()){
                //controlli i vari stati e fai una cosa piuttosto che un altra
            while (client.getClientTask() == clientTask.WAIT){

            }
            while (client.getClientTask() == clientTask.AUTHENTICATE){
                try {
                    ObjectOutputStream output = new ObjectOutputStream(server.getOutputStream());
                    ObjectInputStream input = new ObjectInputStream(server.getInputStream());
                    String newStr = (String)input.readObject();
                    System.out.println(newStr);
                    Scanner scanner = new Scanner(System.in);
                    /* write a String to the server, and then get a String back */
                    String str = scanner.nextLine();
                    while (!"".equals(str)) {
                        output.writeObject(str);
                        String newStr = (String)input.readObject();
                        System.out.println(newStr);
                        str = scanner.nextLine();
                    }
                } catch (IOException e) {
                    System.out.println("server has died");
                } catch (ClassCastException | ClassNotFoundException e) {
                    System.out.println("protocol violation");
                }
            }

        }
    }


}
