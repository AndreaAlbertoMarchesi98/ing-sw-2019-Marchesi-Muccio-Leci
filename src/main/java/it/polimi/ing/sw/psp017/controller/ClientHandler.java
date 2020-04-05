package it.polimi.ing.sw.psp017.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler implements Runnable
{
    private Socket client;
    private Server server;
    private boolean hasJoinedLobby;

    ClientHandler(Socket client, Server server)
    {
        hasJoinedLobby=false;
        this.client = client;
        this.server = server;
    }


    @Override
    public void run()
    {
        try {
            while (server.getState()==ServerState.IDLE)
                handleGameCreation();
            while (server.getState()==ServerState.GAME_CREATION)
                handleWaitingRoom();
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
    private void handleWaitingRoom() throws IOException
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
