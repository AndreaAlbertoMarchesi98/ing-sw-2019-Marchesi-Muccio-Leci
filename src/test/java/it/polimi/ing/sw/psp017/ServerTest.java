package it.polimi.ing.sw.psp017;

import it.polimi.ing.sw.psp017.client.NetworkHandler;
import it.polimi.ing.sw.psp017.client.view.GodName;
import it.polimi.ing.sw.psp017.server.Server;
import it.polimi.ing.sw.psp017.client.view.View;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.AuthenticationMessage;
import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.*;
import org.junit.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ServerTest {
    private final static int SERVER_PORT = 7778;
    private final static String IP = "127.0.0.1";
    private final static int executionTime = 100;
    private static boolean setupDone = false;
    private ArrayList<NetworkHandler> networkHandlers;
    private Server server;
    ArrayList<GodName> godNames;

    private class DummyView implements View {
        public void notifyNickname(AuthenticationMessage authenticationMessage) {

        }

        public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage) {

        }

        public void notifyCard(CardMessage cardMessage) {

        }

        public void notifySelectedTile(SelectedTileMessage selectedTileMessage) {

        }

        public void notifyIsPowerActive(PowerActiveMessage powerActiveMessage) {

        }

        public void notifyRestart(RestartMessage restartMessage) {

        }

        public void notifyUndo(UndoMessage undoMessage) {

        }

        public void updateGameCreation() {

        }

        public void updateLoginScreen(InvalidNameMessage invalidNameMessage) {

        }

        public void updateLobby(LobbyMessage lobbyMessage) {

        }

        public void updateBoard(BoardMessage boardMessage) {

        }

        public void updateDefeat(NoMovesMessage noMovesMessage) {

        }

        public void updateVictory(VictoryMessage victoryMessage) {

        }

        public void updateDisconnection(ServerDisconnectionMessage disconnectionMessage) {

        }
    }

    @Before
    public void init() {
        server = new Server(SERVER_PORT);
        godNames = new ArrayList<>();
        godNames.add(GodName.APOLLO);
        godNames.add(GodName.ARTEMIS);
        godNames.add(GodName.ATHENA);
        networkHandlers = new ArrayList<>();
    }

    @After
    public void reset() throws IOException {
        server.getServerSocket().close();
        server.stop();
    }

    @Test
    public void tonsOfConnectionsTest() throws IOException, InterruptedException {
        for (int i = 0; i < 35; i++) {
            NetworkHandler networkHandler = new NetworkHandler();
            networkHandler.setView(new DummyView());
            networkHandlers.add(networkHandler);
        }
        for (int i = 0; i < 35; i++) {
            networkHandlers.get(i).startConnection(IP, SERVER_PORT);
        }
        for (int i = 30; i < 35; i++) {
            String name = "same_name";
            networkHandlers.get(i).sendMessage(new AuthenticationMessage(name));
        }
        Thread.sleep(executionTime);
        assertEquals("error: there should only be 1 gameController", 1, server.getGameControllers().size());

        for (int i = 0; i < 10; i++) {
            String name = "name" + i;
            networkHandlers.get(i).sendMessage(new AuthenticationMessage(name));
        }
        for (int i = 0; i < 10; i++) {
            networkHandlers.get(i).sendMessage(new GameSetUpMessage(godNames));
        }
        for (int i = 10; i < 30; i++) {
            String name = "name" + i;
            networkHandlers.get(i).sendMessage(new AuthenticationMessage(name));
        }
        Thread.sleep(executionTime);
        assertTrue("error: there should be at least 10 gameControllers", server.getGameControllers().size() >= 10);
    }
}
