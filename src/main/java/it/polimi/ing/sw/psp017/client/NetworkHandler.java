package it.polimi.ing.sw.psp017.client;

import it.polimi.ing.sw.psp017.client.view.GUI;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.client.view.View;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class NetworkHandler implements Runnable{
    private static final int pingInterval = 1000;
    private static final int timeout = 4000;
    private View view;
    private Socket server;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean isConnected;

    public void setView(View view){
        this.view = view;
    }

    /**
     * Send Ping message to the server every 'pingInterval'
     */
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
                    Thread.sleep(pingInterval);
                } catch (InterruptedException e) {

                }
            }
        }
    }


    /**
     * Try to establish a connection with the socket at the ip and serverPort specified
     * @param ipAdress
     * @param serverPort
     * @throws IOException if it can't connect
     */
    public void startConnection(String ipAdress,int serverPort) throws IOException{


        this.server = new Socket(ipAdress, serverPort);
        server.setSoTimeout(timeout);
        this.output = new ObjectOutputStream(server.getOutputStream());
        this.input = new ObjectInputStream(server.getInputStream());
        isConnected = true;

        Thread thread = new Thread(this);
        thread.start();

        Thread pingSenderThread = new Thread(new PingSender(this));
        pingSenderThread.start();

    }

    /**
     * Close socket and Stream securely
     */
    public void closeConnection() {
        try {
            isConnected = false;
            input.close();
            output.close();
            server.close();
        } catch (IOException e) {
        }
    }

    /**
     * Wait for messages from the server and calls the respective View's functions
     * catch an exception if socket time out goes off showing a message to the user
     */
    @Override
    public void run() {
        while (isConnected()){
            try {
                Object message =  input.readObject();
                if(message instanceof InvalidNameMessage){
                    view.updateLoginScreen((InvalidNameMessage)message);
                }
                else if(message instanceof GameCreationMessage){
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
                } else if (message instanceof NoMovesMessage) {
                    view.updateDefeat((NoMovesMessage) message);
                }

            } /*catch (SocketTimeoutException e){

                if(view instanceof GUI){
                    ((GUI)view).showServerNotFound();
                }
                System.out.println("ERROR SERVER PING");
            }*/
            catch (SocketException e){

                if(view instanceof GUI){
                    ((GUI)view).showServerNotFound();
                }else{
                    System.out.println("Error Ping Server! Please retry later");
                }
                closeConnection();
            }catch (IOException | ClassNotFoundException e) {
                closeConnection();
            }

        }
    }

    /**
     * Send message to the socket
     * @param message
     */
    public synchronized void sendMessage(Object message){
        try{
            output.writeObject(message);
            output.reset();
        } catch (IOException e) {

        }
    }

    /**
     * return true if the connection between client and server is on
     * @return
     */
    public boolean isConnected(){
        return isConnected;
    }


}
