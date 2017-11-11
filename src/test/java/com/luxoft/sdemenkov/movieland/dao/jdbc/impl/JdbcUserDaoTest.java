package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcUserDaoTest {
    @Autowired
    UserDao userDao;
    @Test
    public void isUserValid() throws Exception {
        User user = userDao.getUser("ronald.reynolds66@example.com", "paco");
        assertNotNull(user);
        assertEquals("Ronald Reynolds", user.getNickname());
        user = userDao.getUser("ronald.reynolds66@example.com", "xyz");
        assertNull(user);

    }

}