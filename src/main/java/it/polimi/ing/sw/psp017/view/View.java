package it.polimi.ing.sw.psp017.view;

import it.polimi.ing.sw.psp017.controller.messages.ClientToServer.AuthenticationMessage;

public interface View {
//da rinominare con nomi belli
//nomi grafici!!!

//notify methods
    AuthenticationMessage notifyNickname();
//notify example
    /*
    notifyNickname(){
    networkHandler.sendNickname;
    }
     */
//update methods
    //robe grafiche semplicemente cambia quello che si vede sullo schermo
    void updateNamePopup();
    void updateInvalidNickname();
    void updateGameCreation();
    void updateLobby();

}
