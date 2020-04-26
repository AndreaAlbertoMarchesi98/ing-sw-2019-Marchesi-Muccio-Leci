package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.DisconnectionMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.GameCreationMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.InvalidNameMessage;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.view.View;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;


public class NetworkHandler implements Runnable{
    private View view;
    private Socket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean isConnected;

    public NetworkHandler(View view) {
        this.view = view;
        startConnection();
    }
    public void startConnection() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        try {
            this.server = new Socket(ip, Server.SOCKET_PORT);
            server.setSoTimeout(2000);
            this.output = new ObjectOutputStream(server.getOutputStream());
            this.input = new ObjectInputStream(server.getInputStream());
            isConnected = true;
            this.run();

            view.updateLoginScreen(null);
        } catch (IOException  e) {
            //displayServerUnreachble();
            closeConnection();
        }
    }
    public void closeConnection() {
        try {
            System.out.println("server unreachable");
            isConnected = false;
            input.close();
            output.close();
            server.close();
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        while (isConnected()){
            try {
                Object message = (Object) input.readObject();
                if(message instanceof InvalidNameMessage){
                    view.updateLoginScreen((InvalidNameMessage)message);
                }
                else if(message instanceof GameCreationMessage){
                    view.updateGameCreation();
                }

            } catch (SocketTimeoutException e){
                view.notifyDisconnection(new DisconnectionMessage());
            }
            catch (SocketException e){
                view.notifyDisconnection(new DisconnectionMessage());
            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(Object message){
        try{
            output.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isConnected(){
        return isConnected;
    }


}
