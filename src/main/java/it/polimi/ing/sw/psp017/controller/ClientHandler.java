package it.polimi.ing.sw.psp017.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class ClientHandler implements Runnable
{
    private Socket client;
    private Server server;

    ClientHandler(Socket client, Server server)
    {
        this.client = client;
        this.server = server;
    }


    @Override
    public void run()
    {
        try {
            while (server.getState()==ServerState.IDLE)
                handleGameCreation();
            while (server.getState()==ServerState.LOBBY)
                handleCardSelection();
            while (server.getState()==ServerState.GAME)
                handleGameExecution();
        } catch (IOException e) {
            System.out.println("client " + client.getInetAddress() + " connection dropped");
        }
    }

    private void handleGameCreation() throws IOException
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
    private void handleCardSelection() throws IOException
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
