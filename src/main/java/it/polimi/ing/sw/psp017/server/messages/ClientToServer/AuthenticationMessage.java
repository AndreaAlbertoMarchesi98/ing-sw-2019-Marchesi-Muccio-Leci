package it.polimi.ing.sw.psp017.server.messages.ClientToServer;

import java.io.Serializable;

public class AuthenticationMessage implements Serializable {
    public String nickname;
    //password
    public AuthenticationMessage(String nickname){
        this.nickname = nickname;
    }
}
