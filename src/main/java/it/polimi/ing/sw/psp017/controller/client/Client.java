package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.controller.server.Server1;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
enum clientTask {
    WAIT, AUTHENTICATE, CREATE_GAME, LOBBY

}

public class Client implements Observers
{
    private Socket server;
    private boolean isClientConnected = false;
    private Thread threadHandlerMessageClient;
    private clientTask clientTask;

    public static void main( String[] args )
    {
        Client client = new Client();
        client.startConnection();
        client.threadHandlerMessageClient = new Thread(new HandlerMessageClient(client, client.server));
        client.threadHandlerMessageClient.run();

        client.closeConnection();

    }

    private void startConnection(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        /* open a connection to the server */
        //Socket server;
        try {
            server = new Socket(ip, Server1.SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("server unreachable");
            return;
        }
        System.out.println("Connected");
        isClientConnected = true;
    }

    private void closeConnection() {
        try {
            server.close();
        } catch (IOException e) {
        }
        isClientConnected = false;
    }

    public boolean isDisconnected(){
        return !isClientConnected;
    }

    @Override
    public void update(Object message) {
        //in base al messaggio arrivato aggiorna lo stato del client
        //switch (message){
                //DECIDERE COME LEGGERE I MESSAGGI E QUALI SCAMBIARE E COME
      //  }
        /*
        * if(Object istanceof TIPO
        * {
        *   TIPO t = (TIPO) message;
        * }
        * */
    }

    public clientTask getClientTask(){
        return clientTask;
    }
}
