package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.SecurityService;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class SecurityTokenControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private SecurityTokenController securityTokenController;
    @Mock
    private SecurityService securityService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(securityTokenController).build();
    }

    @Test
    public void login() throws Exception {
        when(securityService.loginAsUser(anyString(), anyString())).thenReturn(new User(1, "Test"));
        mockMvc.perform(post("/login")
                .param("email", "darlene.edwards15@example.com")
                .param("password", "bricks"))
                .andExpect(jsonPath("uuid").isNotEmpty())
                .andExpect(jsonPath("nickname").value("Test"));
    }

}