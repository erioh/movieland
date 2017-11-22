package com.luxoft.sdemenkov.movieland.service;


import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.client.Client;

import java.util.UUID;

public interface AuthenticationService {
    Token login(String email, String password);

    void logout(UUID uuid);

    boolean isAlive(UUID uuid);

    void removeInactiveUsers();

    Client getClientByUuid(UUID uuid);

    Client getGuest();

}