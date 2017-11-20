package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserDao userDao;

    @Override
    public void enrichUserWithRoles(User user) {
        userDao.enrichUserWithRoles(user);
        logger.debug("getUsersRoles. User {} has roles {}", user);
    }

    @Override
    public User getUser(String email, String password) {
        User user = userDao.getUser(email, password);
        enrichUserWithRoles(user);
        logger.debug("getUser. User {} is found for email = {}", user, email);
        return user;
    }
}
