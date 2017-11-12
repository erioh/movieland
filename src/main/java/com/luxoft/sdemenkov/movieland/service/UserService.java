package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Token;

import java.util.UUID;

public interface UserService {
    Token login(String email, String password);

    void logout(UUID uuid);

    boolean isAlive(UUID uuid);

    void removeInactiveUsers();
}