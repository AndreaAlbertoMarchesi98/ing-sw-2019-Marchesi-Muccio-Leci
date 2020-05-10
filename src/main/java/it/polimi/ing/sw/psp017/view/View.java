package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;

public interface View {
    /*robe da modificare
    1)power active cia il suo metodo
    */

    //send information to controller
    void notifyNickname(AuthenticationMessage authenticationMessage);
    void notifyGameSetUp(GameSetUpMessage gameSetUpMessage);
    void notifyCard(CardMessage cardMessage);
    void notifyPlacement(PlacementMessage placementMessage);  //messaggio poszionamento pedine
    void notifySelection(SelectionMessage selectionMessage);//!!!!questo diventa solo worker position
    void notifyIsPowerActive(PowerActiveMessage powerActiveMessage);
    void notifyAction(ActionMessage actionMessage);
    void notifyDisconnection(DisconnectionMessage disconnectionMessage);


    //get information from controller
    //void updateFrame();
    void updateGameCreation();  //username valido && primo giocatore
    void updateLoginScreen(InvalidNameMessage invalidNameMessage);
    void updateLobby(LobbyMessage lobbyMessage);//1> tutte le carte, other il resto delle carte
    void updateWaitingRoom(WaitMessage waitMessage);//giocatori in attesa
    void updateBoard(BoardMessage boardMessage);//contiene anche isMove e le valid tiles
    void updateVictory(VictoryMessage victoryMessage);
    void updateDisconnection(SDisconnectionMessage disconnectionMessage);
}
