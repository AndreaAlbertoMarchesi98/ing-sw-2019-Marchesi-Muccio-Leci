package it.polimi.ing.sw.psp017.client.view;

import it.polimi.ing.sw.psp017.server.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.server.messages.ServerToClient.*;

public interface View {
    /*robe da modificare
    1)power active cia il suo metodo
    */

    //send information to controller
    void notifyNickname(AuthenticationMessage authenticationMessage);
    void notifyGameSetUp(GameSetUpMessage gameSetUpMessage);
    void notifyCard(CardMessage cardMessage);
    void notifySelectedTile(SelectedTileMessage selectedTileMessage);  //messaggio poszionamento pedine
    void notifyIsPowerActive(PowerActiveMessage powerActiveMessage);
    void notifyRestart(RestartMessage restartMessage);
    void notifyUndo(UndoMessage undoMessage);


    //get information from controller
    void updateGameCreation();  //username valido && primo giocatore
    void updateLoginScreen(InvalidNameMessage invalidNameMessage);
    void updateLobby(LobbyMessage lobbyMessage);//1> tutte le carte, other il resto delle carte
    void updateBoard(BoardMessage boardMessage);//contiene anche isMove e le valid tiles
    void updateDefeat(NoMovesMessage noMovesMessage);
    void updateVictory(VictoryMessage victoryMessage);
    void updateDisconnection(ServerDisconnectionMessage disconnectionMessage);
}
