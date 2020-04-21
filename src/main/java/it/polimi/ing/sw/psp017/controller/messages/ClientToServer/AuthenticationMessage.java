package it.polimi.ing.sw.psp017.controller.messages.ClientToServer;

public class AuthenticationMessage {
    public String nickname;
    //password
    public AuthenticationMessage(String nickname){
        this.nickname = nickname;
    }
}
