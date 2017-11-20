package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.User;

public interface UserDao {

    User getUser(String username, String password);

    void enrichUserWithRoles(User user);
}
