package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.service.UserService;
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
public class UserControllerTest {
    @Mock
    UserService mockedUserService;
    private MockMvc mockMvc;
    @InjectMocks
    private UserController userController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void loginOk() throws Exception {
        when(mockedUserService.login(anyString(), anyString())).thenReturn(new Token(UUID.randomUUID(), "Token"));
        mockMvc.perform(post("/login").param("email", "email").param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("nickname").value("Token"));

    }

    @Test
    public void loginFail() throws Exception {
        when(mockedUserService.login(anyString(), anyString())).thenThrow(new RuntimeException("Login or password are invalid"));
        mockMvc.perform(post("/login").param("email", "email").param("password", "password"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("response").value("Login or password are invalid"));

    }

    @Test
    public void logout() throws Exception {
        mockMvc.perform(delete("/logout").header("x-auth-token", UUID.randomUUID().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("response").value("User is logged out"));

    }

}