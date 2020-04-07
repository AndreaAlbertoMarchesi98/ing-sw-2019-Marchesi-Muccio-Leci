package it.polimi.ing.sw.psp017.controller;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ValidTiles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class Server {

    public final static int SOCKET_PORT = 7777;

    private ArrayList<Player> playersInGame;

    private Queue<Player> waitingPlayers;

    private ServerState state;

    public ServerState getState() {
        return state;
    }

    public void setState(ServerState state) {
        this.state = state;
    }

    public static boolean isTurnFinished(Player player, int stepNumber, boolean isPowerActive) {
        return !player.getCard().canMove(stepNumber, isPowerActive) && !player.getCard().canBuild(stepNumber, isPowerActive);
    }

    public static ValidTiles calculateValidMoves(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Vector2d workerPosition = currentTile.getPosition();
        Card card = currentTile.getWorker().getOwner().getCard();
        ValidTiles validTiles = new ValidTiles(true, currentTile.getPosition());
        for (int x = 0; x < ValidTiles.size; x++) {
            for (int y = 0; y < ValidTiles.size; y++) {
                if (x != 1 && y != 1) {
                    Vector2d leftBottomPosition = Vector2d.subtractVectors(workerPosition, new Vector2d(1, 1));
                    Vector2d position = Vector2d.sumVectors(leftBottomPosition, new Vector2d(x, y));

                    currentStep.setTargetTile(board.getTile(position));
                    boolean isValidMove = card.isValidMove(currentStep, previousStep, board);
                    validTiles.setTile(isValidMove, new Vector2d(x, y));
                }
            }
        }
        return validTiles;
    }

    public static ValidTiles calculateValidBuilds(Step currentStep, Step previousStep, Board board) {
        Tile currentTile = currentStep.getCurrentTile();
        Vector2d workerPosition = currentTile.getPosition();
        Card card = currentTile.getWorker().getOwner().getCard();
        ValidTiles validTiles = new ValidTiles(false, currentTile.getPosition());
        for (int x = 0; x < ValidTiles.size; x++) {
            for (int y = 0; y < ValidTiles.size; y++) {
                if (x != 1 && y != 1) {
                    Vector2d leftBottomPosition = Vector2d.subtractVectors(workerPosition, new Vector2d(1, 1));
                    Vector2d position = Vector2d.sumVectors(leftBottomPosition, new Vector2d(x, y));

                    currentStep.setTargetTile(board.getTile(position));
                    boolean isValidMove = card.isValidBuilding(currentStep, previousStep, board);
                    validTiles.setTile(isValidMove, new Vector2d(x, y));
                }
            }
        }
        return validTiles;
    }

    public static void main(String[] args) {
        Server server = new Server();
        ServerSocket socket;
        try {
            socket = new ServerSocket(SOCKET_PORT);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
            return;
        }


        while (true) {
            try {
                /* accepts connections; for every connection we accept,
                 * create a new Thread executing a ClientHandler */
                Socket client = socket.accept();
                ClientHandler clientHandler = new ClientHandler(client, server);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }


}
