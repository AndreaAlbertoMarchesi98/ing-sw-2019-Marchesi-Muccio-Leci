package it.polimi.ing.sw.psp017.controller.client;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.view.View;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class NetworkHandler implements Runnable{
    private final static int SERVER_PORT = 7778;
    private View view;
    private Socket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean isConnected;

    public void setView(View view){
        this.view = view;
    }

    private static class PingSender implements Runnable{
        private final NetworkHandler networkHandler;

        public PingSender(NetworkHandler networkHandler){
            this.networkHandler = networkHandler;
        }
        @Override
        public void run() {
            while (true) {
                try {
                    networkHandler.sendMessage(new ClientPing());
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void startConnection() throws IOException{

        String ip = "127.0.0.1";
        //String ip = "192.168.1.4";

            this.server = new Socket(ip, SERVER_PORT);
            server.setSoTimeout(1000);
            this.output = new ObjectOutputStream(server.getOutputStream());
            this.input = new ObjectInputStream(server.getInputStream());
            isConnected = true;

            Thread thread = new Thread(this);
            thread.start();

            Thread pingSenderThread = new Thread(new PingSender(this));
            pingSenderThread.start();

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
                else if(message instanceof ServerDisconnectionMessage){
                    view.updateDisconnection((ServerDisconnectionMessage)message);
                }else if(message instanceof VictoryMessage) {
                    view.updateVictory((VictoryMessage) message);
                }

            } catch (SocketTimeoutException e){
                System.out.println("ERROR SERVER PING");
                System.exit(0);
            }
            catch (SocketException e){
                System.exit(0);
            }catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                closeConnection();
            }

        }
    }

    public synchronized void sendMessage(Object message){
        try{
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public boolean isConnected(){
        return isConnected;
    }


}
