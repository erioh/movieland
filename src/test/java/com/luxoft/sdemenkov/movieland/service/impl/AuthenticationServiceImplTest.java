package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Test
    public void login() throws Exception {
        when(userDao.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Field milliSecondsToLogout = userService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = userService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(userService, 100000L);
        hoursBeforeLogout.set(userService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        Token token = userService.login("login", "password");
        assertNotNull(token.getUuid());
        assertEquals("userName", token.getUser().getNickname());

    }

    @Test(expected = RuntimeException.class)
    public void loginFailed() throws Exception {
        when(userDao.getUser(anyString(), anyString())).thenReturn(null);
        Token token = userService.login("Login", "password");
    }


    @Test
    public void logout() throws Exception {
        Field milliSecondsToLogout = userService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = userService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(userService, 100000L);
        hoursBeforeLogout.set(userService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        when(userDao.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Token token = userService.login("login", "password");
        userService.logout(token.getUuid());
    }

    @Test
    public void isAlive() throws Exception {
        Field milliSecondsToLogout = userService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = userService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(userService, 100000L);
        hoursBeforeLogout.set(userService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        when(userDao.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Token token = userService.login("login", "password");
        assertTrue(userService.isAlive(token.getUuid()));
        assertTrue(userService.isAlive(token.getUuid()));

        Field timeToDie = token.getClass().getDeclaredField("dieTime");
        timeToDie.setAccessible(true);
        timeToDie.set(token, LocalDateTime.now().minusHours(1).minusHours(1));
        timeToDie.setAccessible(false);

        assertFalse(userService.isAlive(token.getUuid()));
        assertFalse(userService.isAlive(UUID.randomUUID()));
    }

}