package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcUserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void getUser() throws Exception {
        User user = userDao.getUser("ronald.reynolds66@example.com", "paco");
        assertNotNull(user);
        assertEquals("Рональд Рейнольдс", user.getNickname());
        assertEquals("ronald.reynolds66@example.com", user.getEmail());
        user = userDao.getUser("ronald.reynolds66@example.com", "xyz");
        assertNull(user);

    }

    @Test
    public void enrichUserWithRoles() throws Exception {
        User user = new User();
        user.setId(1);
        userDao.enrichUserWithRoles(user);
        List<Role> roleList = user.getRoleList();
        assertEquals(Role.USER, roleList.get(0));

    }

}