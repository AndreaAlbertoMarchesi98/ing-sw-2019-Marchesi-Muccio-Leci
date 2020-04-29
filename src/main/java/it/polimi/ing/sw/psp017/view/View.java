package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;

public interface View {

    //send information to controller
    void notifyNickname(AuthenticationMessage authenticationMessage);

    void notifyGameSetUp(GameSetUpMessage gameSetUpMessage);
    void notifyCard(CardMessage cardMessage);
    void notifyPlacement(PlacementMessage placementMessage);  //messaggio poszionamento pedine
    void notifySelection(SelectionMessage selectionMessage);
    void notifyAction(ActionMessage actionMessage);
    void notifyDisconnection(DisconnectionMessage disconnectionMessage);


    //get information from controller
    //void updateFrame();
    void updateGameCreation();  //username valido && primo giocatore
    void updateLoginScreen(InvalidNameMessage invalidNameMessage);
    void updateLobby(LobbyMessage lobbyMessage);//1> tutte le carte, other il resto delle carte
    void updateWaitingRoom(WaitMessage waitMessage);//giocatori in attesa
    void updateValidTiles(ValidTilesMessage validTilesMessage);
    void updateBoard(BoardMessage boardMessage);
}
