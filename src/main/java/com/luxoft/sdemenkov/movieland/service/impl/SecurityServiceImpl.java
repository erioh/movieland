package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserDao userDao;

    public User loginAsUser(String email, String password) {
        User user = userDao.getUser(email, password);
        logger.debug("Getting of user with email {} and password {} returns {}", email, password, user);
        return user;
    }
}
