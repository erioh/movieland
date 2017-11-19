package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.UserService;
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
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserService userService;

    @Test
    public void login() throws Exception {
        when(userService.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Field milliSecondsToLogout = authenticationService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = authenticationService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(authenticationService, 100000L);
        hoursBeforeLogout.set(authenticationService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        Token token = authenticationService.login("login", "password");
        assertNotNull(token.getUuid());
        assertEquals("userName", token.getUser().getNickname());

    }

    @Test(expected = RuntimeException.class)
    public void loginFailed() throws Exception {
        when(userService.getUser(anyString(), anyString())).thenReturn(null);
        Token token = authenticationService.login("Login", "password");
    }


    @Test
    public void logout() throws Exception {
        Field milliSecondsToLogout = authenticationService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = authenticationService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(authenticationService, 100000L);
        hoursBeforeLogout.set(authenticationService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        when(userService.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Token token = authenticationService.login("login", "password");
        authenticationService.logout(token.getUuid());
    }

    @Test
    public void isAlive() throws Exception {
        Field milliSecondsToLogout = authenticationService.getClass().getDeclaredField("milliSecondsToLogout");
        Field hoursBeforeLogout = authenticationService.getClass().getDeclaredField("hoursBeforeLogout");
        hoursBeforeLogout.setAccessible(true);
        milliSecondsToLogout.setAccessible(true);
        milliSecondsToLogout.set(authenticationService, 100000L);
        hoursBeforeLogout.set(authenticationService, 1);
        milliSecondsToLogout.setAccessible(false);
        hoursBeforeLogout.setAccessible(false);
        when(userService.getUser(anyString(), anyString())).thenReturn(new User(1, "userName"));
        Token token = authenticationService.login("login", "password");
        assertTrue(authenticationService.isAlive(token.getUuid()));
        assertTrue(authenticationService.isAlive(token.getUuid()));

        Field timeToDie = token.getClass().getDeclaredField("dieTime");
        timeToDie.setAccessible(true);
        timeToDie.set(token, LocalDateTime.now().minusHours(1).minusHours(1));
        timeToDie.setAccessible(false);

        assertFalse(authenticationService.isAlive(token.getUuid()));
        assertFalse(authenticationService.isAlive(UUID.randomUUID()));
    }

}