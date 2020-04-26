package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.client.Client;
import it.polimi.ing.sw.psp017.controller.client.NetworkHandler;
import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;


public class CLI implements View{

    private Client client;

    public CLI(Client client){
        this.client = client;
        printLogo();
    }


    public void printLogo(){
        CommandLineInterface.printLogo();
    }


    @Override
    public void notifyNickname(AuthenticationMessage authenticationMessage) {
        client.getNetworkHandler().sendMessage(authenticationMessage);
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
        client.getNetworkHandler().closeConnection();
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