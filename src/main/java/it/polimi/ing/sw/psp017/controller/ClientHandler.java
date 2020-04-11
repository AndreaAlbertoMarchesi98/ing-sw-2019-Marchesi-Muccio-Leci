package it.polimi.ing.sw.psp017.controller;

import java.io.*;
import java.net.Socket;


public class ClientHandler implements Runnable
{
    private Socket client;
    private Server server;
    private boolean hasJoinedLobby;
    private ClientTask clientTask;

    public ClientTask getClientTask() {
        return clientTask;
    }

    public void setClientTask(ClientTask clientTask) {
        this.clientTask = clientTask;
    }

    public enum ClientTask {
        WAIT, AUTHENTICATE, CREATE_GAME, LOBBY
    }

    ClientHandler(Socket client, Server server)
    {
        hasJoinedLobby=false;
        this.client = client;
        this.server = server;
        this.clientTask = ClientTask.AUTHENTICATE;
    }


    @Override
    public void run()
    {
        try {

            while (clientTask==ClientTask.AUTHENTICATE)
                handleAuthentication();
            while (clientTask==ClientTask.WAIT)
                handleWaitingRoom();
            while (clientTask==ClientTask.CREATE_GAME)
                handleGameCreation();
            while (server.getState()==ServerState.LOBBY)
                handleLobby();
            while (server.getState()==ServerState.GAME_EXECUTION) {
                if(hasJoinedLobby)
                    handleGameExecution();
                else
                    handleWaitingRoom();
            }
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }
    private synchronized void handleAuthentication() throws IOException
    {
        System.out.println("client: "+client.getInetAddress()+" ");

        DataOutputStream output = new DataOutputStream(client.getOutputStream());
        DataInputStream input = new DataInputStream(client.getInputStream());

        try {
            output.writeUTF("server authentication message!");
            output.flush(); // send the message

            String message = input.readUTF();
            System.out.println(message);

        } catch (ClassCastException e) {
            System.out.println("invalid stream from client");
        }
        //controllo sul nome
        server.addWaitingClients(this);

        client.close();
    }
    private synchronized void handleWaitingRoom() throws IOException
    {
        if(server.getState()==ServerState.IDLE)
            System.out.println("Game creation has started");
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        try {
            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;
                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }
        //controllo sul nome
        server.addWaitingClients(this);

        client.close();
    }
    private synchronized void handleGameCreation() throws IOException
    {
        System.out.println("Game creation has started");
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        try {
            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;
                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        client.close();
    }
    private void handleLobby() throws IOException
    {
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        hasJoinedLobby=true;
        try {

            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;
                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        client.close();
    }
    private void handleGameExecution() throws IOException
    {
        System.out.println("Connected to " + client.getInetAddress());

        ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(client.getInputStream());

        try {

            while (true) {
                /* read a String from the stream and write an uppercase string in response */
                Object next = input.readObject();
                String str = (String)next;
                output.writeObject(str.toUpperCase());
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("invalid stream from client");
        }

        client.close();
    }
}
