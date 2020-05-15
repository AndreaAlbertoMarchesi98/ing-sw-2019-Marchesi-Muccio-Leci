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

    public void setView(View view){
        this.view = view;
    }
    private static class PingSender implements Runnable{
        private NetworkHandler networkHandler;

        public PingSender(NetworkHandler networkHandler){
            this.networkHandler = networkHandler;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    networkHandler.sendMessage(new Ping());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    public NetworkHandler(View view) {
        this.view = view;
    }

    public void startConnection() throws IOException{

        Scanner scanner = new Scanner(System.in);
        System.out.println("IP address of server?");
        String ip = "127.0.0.1";//scanner.nextLine();

            this.server = new Socket(ip, Server.SOCKET_PORT);
            //server.setSoTimeout(2000);
            this.output = new ObjectOutputStream(server.getOutputStream());
            this.input = new ObjectInputStream(server.getInputStream());
            isConnected = true;

            Thread thread = new Thread(this);
            thread.start();

            Thread pingSenderThread = new Thread(new PingSender(this));
            pingSenderThread.start();

            System.out.println();
            view.updateLoginScreen(null);

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
                    view.updateLobby((LobbyMessage) message);
                }
                else if(message instanceof BoardMessage){
                    view.updateBoard((BoardMessage) message);
                }
                else if(message instanceof WaitMessage){
                    view.updateWaitingRoom((WaitMessage)message);
                }
                else if(message instanceof SDisconnectionMessage){
                    System.out.println("disconnection message has arrived, it was player: " + ((SDisconnectionMessage) message).disconnectedPlayerNumber);
                }else if(message instanceof VictoryMessage){
                    view.updateVictory((VictoryMessage)message);
                }

            } catch (SocketTimeoutException e){
                e.printStackTrace();
                view.notifyDisconnection(new DisconnectionMessage());
            }
            catch (SocketException e){
                view.notifyDisconnection(new DisconnectionMessage());
            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                closeConnection();
            }

        }
    }

    public void sendMessage(Object message){
        try{
            output.writeObject(message);
            //System.out.println("wait for Server response");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isConnected(){
        return isConnected;
    }


}
