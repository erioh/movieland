package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class AuthenticationControllerTest {
    @Mock
    private AuthenticationService mockedAuthenticationService;
    private MockMvc mockMvc;
    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void loginOk() throws Exception {
        when(mockedAuthenticationService.login(anyString(), anyString())).thenReturn(new Token(new User(1, "Token"), UUID.randomUUID(), LocalDateTime.now()));
        mockMvc.perform(post("/login").param("email", "email").param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("Token"));

    }

    @Test
    public void loginFail() throws Exception {
        when(mockedAuthenticationService.login(anyString(), anyString())).thenThrow(new RuntimeException("Login or password are invalid"));
        mockMvc.perform(post("/login").param("email", "email").param("password", "password"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("exceptionMessage").value("Login or password are invalid"));

    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(delete("/logout").header("x-auth-token", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("User is logged out"));

    }

}