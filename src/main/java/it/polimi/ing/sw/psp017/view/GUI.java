package it.polimi.ing.sw.psp017.view;
import it.polimi.ing.sw.psp017.controller.Client.Client;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.BoardMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.LobbyMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.ValidTilesMessage;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.WaitMessage;
import it.polimi.ing.sw.psp017.model.Vector2d;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class GUI extends JFrame implements View{

    private Client client;

        private ImageIcon img;

        public GUI() {
            displayLogo();
             client = new Client();
             client.setView(this);

        }


    public void displayLogo()  {
        //JFrame frame = new JFrame("Demo Background Image");

        // loadImage(img);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,287);
        img = new ImageIcon(("\"C:\\Users\\user\\OneDrive - Politecnico di Milano\\Desktop\\Annotazione 2020-04-09 011536.jpg\""));
        add(new JLabel("", img, JLabel.CENTER));

        setVisible(true);
        }


    @Override
    public void notifyNickname(AuthenticationMessage authenticationMessage) {

    }

    @Override
    public void notifyGameSetUp(GameSetUpMessage gameSetUpMessage) {

    }

    @Override
    public void notifyCard(CardMessage cardMessage) {

    }

    @Override
    public void notifySelection(SelectionMessage selectionMessage) {

    }

    @Override
    public void notifyAction(ActionMessage actionMessage) {

    }

    @Override
    public void notifyDisconnection(DisconnectionMessage disconnectionMessage) {

    }

    @Override
    public void updateGameCreation() {

    }

    @Override
    public void updateLoginScreen() {

    }

    @Override
    public void updateLobby(LobbyMessage lobbyMessage) {

    }

    @Override
    public void updateWaitingList(WaitMessage waitMessage) {

    }

    @Override
    public void updateValidTiles(ValidTilesMessage validTilesMessage) {

    }

    @Override
    public void updateBoard(BoardMessage boardMessage) {

    }
}
