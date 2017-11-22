package com.luxoft.sdemenkov.movieland.security.client;


import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.role.Role;

import java.util.List;
import java.util.Optional;

public class Client {
    private Optional<Token> token = Optional.empty();
    private String clientIdentificator;
    private boolean isGuest;
    private List<Role> roleList;

    public Client() {
        clientIdentificator = "guest";
        isGuest = true;
    }
    public Client(Token token) {
        this.token = Optional.of(token);
        this.clientIdentificator = token.getUser().getEmail();
        this.roleList = token.getUser().getRoleList();
        isGuest = false;
    }

    public Optional<Token> getToken() {
        return token;
    }

    public boolean isGuest() {
        return isGuest;
    }

    public String getClientIdentificator() {
        return clientIdentificator;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    @Override
    public String toString() {
        return "Client{" +
                "token=" + token +
                ", clientIdentificator='" + clientIdentificator + '\'' +
                ", isGuest=" + isGuest +
                ", roleList=" + roleList +
                '}';
    }
}
