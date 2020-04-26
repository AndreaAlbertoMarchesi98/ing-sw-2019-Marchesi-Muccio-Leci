package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.Client.Client;
import it.polimi.ing.sw.psp017.controller.Client.NetworkHandler;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;
import it.polimi.ing.sw.psp017.model.Vector2d;

import java.util.ArrayList;
import java.util.Scanner;

public class CLI implements View{
    private Client client;
    private NetworkHandler networkHandler;

    public CLI(){
        printLogo();
        client = new Client();
        client.setView(this);

    }


    public void printLogo(){
        CommandLineInterface.printLogo();
    }


    @Override
    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        networkHandler.sendMessage(authenticationMessage);
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
        networkHandler.closeConnection();
    }

    @Override
    public void updateGameCreation() {

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

    public void updateLoginScreen(InvalidNameMessage invalidNameMessage){
        if(invalidNameMessage != null){
            System.out.println("Invalid Nickname");
        }
        String nickname = CommandLineInterface.getNickname();
        AuthenticationMessage am = new AuthenticationMessage(nickname);
        notifyNickname(am);
    }



}