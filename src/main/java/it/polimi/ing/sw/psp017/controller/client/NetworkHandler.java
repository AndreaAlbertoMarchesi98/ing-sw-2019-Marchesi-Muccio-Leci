package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.controller.messages.*;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.controller.server.Server;
import it.polimi.ing.sw.psp017.view.ValidTiles;
import it.polimi.ing.sw.psp017.view.View;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
    }

    public void startConnection() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address of server?");
        String ip = scanner.nextLine();

        try {
            this.server = new Socket(ip, Server.SOCKET_PORT);
            //server.setSoTimeout(2000);
            this.output = new ObjectOutputStream(server.getOutputStream());
            this.input = new ObjectInputStream(server.getInputStream());
            isConnected = true;

            Thread thread = new Thread(this);
            thread.start();

            System.out.println();
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
                Object message =  input.readObject();
                if(message instanceof InvalidNameMessage){
                    view.updateLoginScreen((InvalidNameMessage)message);
                }
                else if(message instanceof GameCreationMessage){
                    System.out.println("game creation message");
                    view.updateGameCreation();
                }
                else if(message instanceof LobbyMessage){
                    System.out.println("lobby message");
                    view.updateLobby((LobbyMessage) message);
                }
                else if(message instanceof BoardMessage){
                    view.updateBoard((BoardMessage) message);
                }
                else if(message instanceof LobbyMessage){
                    view.updateLobby((LobbyMessage)message);
                }
                else if(message instanceof ValidTilesMessage){
                    view.updateValidTiles((ValidTilesMessage) message);
                }
                else if(message instanceof WaitMessage){
                    view.updateWaitingRoom((WaitMessage)message);
                }

            } catch (SocketTimeoutException e){
                e.printStackTrace();
                view.notifyDisconnection(new DisconnectionMessage());
            }
            catch (SocketException e){
                e.printStackTrace();
                view.notifyDisconnection(new DisconnectionMessage());
            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendMessage(Object message){
        try{
            output.writeObject(message);
            System.out.println("wait for Server response");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isConnected(){
        return isConnected;
    }


}
