package com.luxoft.sdemenkov.movieland.security;


import com.luxoft.sdemenkov.movieland.model.technical.Token;

import java.security.Principal;
import java.util.Optional;

public class TokenPrincipal implements Principal {
    private Optional<Token> token;

    public TokenPrincipal(Optional<Token> token) {
        this.token = token;

    }

    public Optional<Token> getToken() {
        return token;
    }

    public void setToken(Optional<Token> token) {
        this.token = token;
    }

    @Override
    public String getName() {
        return token.map(token -> token.getUser().getEmail()).orElse("guest");
    }

    @Override
    public String toString() {
        return "TokenPrincipal{" +
                "token=" + (token.isPresent() ? token.get() : "null") +
                '}';
    }
}
