package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.ReviewSecurityFilter;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.client.Client;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.impl.AuthenticationServiceImpl;
import com.luxoft.sdemenkov.movieland.web.dto.request.RateDto;
import com.luxoft.sdemenkov.movieland.web.interceptor.RequestInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class ProtectedMovieControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private MovieController movieController;
    @InjectMocks
    private RequestInterceptor requestInterceptor;
    @Mock
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private MovieService movieService;


    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).addInterceptors(requestInterceptor).addFilters(new ReviewSecurityFilter()).build();
    }

    @Test
    public void rateMovie() throws Exception {
        User user = new User();
        user.setId(1);
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        user.setRoleList(roleList);

        Optional<Token> token = Optional.of(new Token(user));
        Client client = new Client(token.get());
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);

        ObjectMapper mapper = new ObjectMapper();
        RateDto rateDto = new RateDto(8);
        String json = mapper.writeValueAsString(rateDto);

        when(authenticationService.getClientByUuid(any())).thenReturn(client);

        mockMvc.perform(post("/movie/1/rate")
                .principal(tokenPrincipal)
                .header("x-auth-token", "f7367061-ac52-4284-827e-3bc23d841dc1")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE).content(json))
                .andExpect(status().isOk());
    }
}
