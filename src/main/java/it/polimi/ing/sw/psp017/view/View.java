package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.*;
import it.polimi.ing.sw.psp017.controller.messages.ServerToClient.*;

public interface View {

    void notifyNickname(AuthenticationMessage authenticationMessage);
    void notifyGameSetUp(GameSetUpMessage gameSetUpMessage);
    void notifyCard(CardMessage cardMessage);
    void notifySelection(SelectionMessage selectionMessage);
    void notifyAction(ActionMessage actionMessage);
    void notifyDisconnection(DisconnectionMessage disconnectionMessage);


    //void updateFrame();
    void updateGameCreation();
    void updateLoginScreen(InvalidNameMessage invalidNameMessage);
    void updateLobby(LobbyMessage lobbyMessage);
    void updateWaitingList(WaitMessage waitMessage);
    void updateValidTiles(ValidTilesMessage validTilesMessage);
    void updateBoard(BoardMessage boardMessage);
}
