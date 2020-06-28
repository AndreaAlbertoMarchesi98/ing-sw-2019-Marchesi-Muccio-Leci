package it.polimi.ing.sw.psp017.server;

import it.polimi.ing.sw.psp017.server.controller.GameController;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.InvalidNameMessage;
import it.polimi.ing.sw.psp017.server.model.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private static final int timeout = 4000;
    private ServerSocket socket;
    private final static int SOCKET_PORT = 7778;
    private final static int undoWaitTime = 5000;

    private boolean running = true;

    private final ArrayList<GameController> gameControllers;
    private final ArrayList<GameController> waitingGameControllers;

    public static void main(String[] args) {
        new Server(SOCKET_PORT);
    }

    /**
     * initialize server's lists, create server's socket and start AcceptConnectionsThread
     *
     * @param socketPort the port where the server socket will run
     */
    public Server(int socketPort) {
        waitingGameControllers = new ArrayList<>();
        gameControllers = new ArrayList<>();
        try {
            socket = new ServerSocket(socketPort);
        } catch (IOException e) {
            System.out.println("cannot open server socket");
            System.exit(1);
        }

        new Thread(new AcceptConnectionsThread(this)).start();
    }

    private static class AcceptConnectionsThread implements Runnable {
        private final Server server;

        public AcceptConnectionsThread(Server server) {
            this.server = server;
        }

        public void run() {
            server.AcceptConnections();
        }
    }

    /**
     * stops server's execution
     *
     */
    public void stop(){
        running = false;
    }

    /**
     * initialize server's lists, create server's socket and start AcceptConnectionsThread
     *
     * @param view the disconnected view
     */
    public synchronized void handleDisconnection(VirtualView view) {
        GameController gameController = view.getGameController();
        if (gameController != null)
            gameController.handleDisconnection(view);
    }

    public ServerSocket getServerSocket(){
        return socket;
    }
    /**
     * @return the gameControllers
     */
    public ArrayList<GameController> getGameControllers() {
        return gameControllers;
    }

    /**
     * remove game controller form both controllers lists
     *
     * @param gameController the gameController to be removed
     */
    public void removeGameController(GameController gameController) {
        gameControllers.remove(gameController);
        waitingGameControllers.remove(gameController);
    }

    /**
     * add game controller to waitingGameControllers so that players can join it
     *
     * @param gameController the gameController to be added
     */
    public void addWaitingGameController(GameController gameController) {
        waitingGameControllers.add(gameController);
    }

    /**
     * create a new gameController and add it to gameControllers so that a new game can start
     *
     * @param view the view that sets up a new game
     */
    private void createGame(VirtualView view) {
        GameController gameController = new GameController(this, view,undoWaitTime);
        gameControllers.add(gameController);
    }

    /**
     * add the view to the first waitingGameController, then delete it if it's full
     *
     * @param view the view that joins a game
     */
    private void joinGame(VirtualView view) {
        waitingGameControllers.get(0).addViewToLobby(view);
        if (!waitingGameControllers.get(0).isLobbyJoinable()) {
            waitingGameControllers.remove(0);
        }
    }

    /**
     * if there are no waitingGameControllers create a new one
     * else if there is one joinable join it
     * else there are none joinable, so create a new one
     *
     * @param view the view to be assigned
     */
    public synchronized void assignView(VirtualView view) {
        System.out.println("assigningView");
        if (waitingGameControllers.isEmpty())
            createGame(view);
        else if (waitingGameControllers.get(0).isLobbyJoinable())
            joinGame(view);
        else
            createGame(view);
    }

    /**
     * check if view's nickname is unique among all players,
     * if it is create new player, assign it to the view, and call assign view method
     * if it's not notify the view that it's username isn't unique
     *
     * @param nickname the view's nickname
     * @param view     the view to be authenticated
     */
    public synchronized void tryAuthenticatingView(String nickname, VirtualView view) {
        System.out.println("authenticating view");
        if (isNicknameUnique(nickname)) {
            Player player = new Player(nickname);
            view.setPlayer(player);
            assignView(view);
        } else {
            view.updateLoginScreen(new InvalidNameMessage());
        }
    }

    /**
     * loop though all players to check if username's unique
     *
     * @param nickname the view's nickname
     * @return true if username is unique false otherwise
     */
    private boolean isNicknameUnique(String nickname) {
        for (GameController gameController : gameControllers) {
            for (VirtualView view : gameController.getViews()) {
                if (nickname.equals(view.getPlayer().getNickname()))
                    return false;
            }
        }
        return true;
    }

    /**
     * infinite loop that keeps checking for new connections,
     * if there's a new connection the virtualView thread is created so that it is handled
     */
    public void AcceptConnections() {
        while (running) {
            try {
                Socket client = socket.accept();

                client.setSoTimeout(timeout);
                System.out.println("new client connected");

                VirtualView virtualView = new VirtualView(client, this);
                Thread thread = new Thread(virtualView, "server_" + client.getInetAddress());
                thread.start();

            } catch (IOException e) {
                System.out.println("connection dropped");
            }
        }
    }
}
