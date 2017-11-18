package com.luxoft.sdemenkov.movieland.security;


import com.luxoft.sdemenkov.movieland.model.Token;

import java.security.Principal;

public class TokenPrincipal implements Principal {
    private Token token;

    public TokenPrincipal(Token token) {
        this.token = token;

    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return token.getUuid().toString();
    }

    @Override
    public String toString() {
        return "TokenPrincipal{" +
                "token=" + token +
                '}';
    }
}
