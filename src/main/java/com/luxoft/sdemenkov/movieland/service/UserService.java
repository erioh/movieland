package com.luxoft.sdemenkov.movieland.service;


import com.luxoft.sdemenkov.movieland.model.User;

public interface UserService {
    void enrichUserWithRoles(User user);

    User getUser(String email, String password);
}
