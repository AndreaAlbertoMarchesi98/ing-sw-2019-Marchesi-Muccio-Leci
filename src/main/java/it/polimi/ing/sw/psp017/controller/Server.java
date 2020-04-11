package it.polimi.ing.sw.psp017.controller;

import it.polimi.ing.sw.psp017.model.*;
import it.polimi.ing.sw.psp017.view.ValidTiles;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Queue;

public class Server {

    private Game game;

    public final static int SOCKET_PORT = 7777;

    private ArrayList<Player> playersInGame;
    private ArrayList<ClientHandler> clientsInGame;
    private Queue<ClientHandler> waitingClients;

    private ServerState state;

    public ServerState getState() {
        return state;
    }

    public void createGame(int playersCount){
        game = Game.getInstance(playersCount);
    }
    public Queue<ClientHandler> getWaitingClients() {
        return waitingClients;
    }

    public void addWaitingClients(ClientHandler waitingClients) {
        this.waitingClients.add(waitingClients);
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
                server.updateServerState(server);
                Socket client = socket.accept();

                System.out.println("new client connected");

                ClientHandler clientHandler = new ClientHandler(client, server);
                Thread thread = new Thread(clientHandler, "server_" + client.getInetAddress());
                thread.start();
            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }


    }

    private void callWaitingClient(ClientHandler.ClientTask clientTask){
        ClientHandler client = waitingClients.element();
        client.setClientTask(clientTask);
        clientsInGame.add(client);
    }

    private void updateServerState(Server server){
        while (server.state==ServerState.IDLE){
            if(!waitingClients.isEmpty()){
                callWaitingClient(ClientHandler.ClientTask.CREATE_GAME);
                server.state=ServerState.GAME_CREATION;
            }
        }
        while (server.state==ServerState.GAME_CREATION){
            //potrebbe starci dire a chi aspetta che un game sta venendo creato
            if(game.isCreated()){
                //creare lobby
                server.state = ServerState.LOBBY;
            }
        }
        while (server.state==ServerState.LOBBY){
            if(clientsInGame.size()<game.getPlayersCount()){
                if(!waitingClients.isEmpty())
                    callWaitingClient(ClientHandler.ClientTask.LOBBY);
            }
            /*if(lobby e pronta){
                server.setState(ServerState.GAME_EXECUTION);
            }
             */
        }
        while (server.state==ServerState.GAME_EXECUTION) {
        }
    }


}
