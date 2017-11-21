package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.ReviewSecurityFilter;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.service.impl.AuthenticationServiceImpl;
import com.luxoft.sdemenkov.movieland.service.impl.ReviewServiceImpl;
import com.luxoft.sdemenkov.movieland.web.controller.rest.ReviewController;
import com.luxoft.sdemenkov.movieland.web.interceptor.RequestInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ReviewController reviewController;


    @InjectMocks
    private RequestInterceptor requestInterceptor;

    @Mock
    private ReviewServiceImpl mockedReviewService;

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(reviewController).addInterceptors(requestInterceptor).addFilters(new ReviewSecurityFilter()).build();
    }

    @Test
    public void saveReviewReviewIsAdded() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"ReviewControllerTest\"}";
        User user = new User();
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        user.setRoleList(roleList);
        Token token = new Token(user);
        when(authenticationService.getTokenByUuid(any())).thenReturn(token);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);

        mockMvc.perform(post("/review").principal(tokenPrincipal).header("x-auth-token", "f7367061-ac52-4284-827e-3bc23d841dc1")
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("message").value("Review is saved"));
    }

    @Test
    public void saveReviewReviewUserIsNotAllowedToAddReviews() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"ReviewControllerTest\"}";
        User user = new User();
        user.setEmail("test@mail.com");
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.ADMIN);
        user.setRoleList(roleList);
        Token token = new Token(user);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);
        when(authenticationService.getTokenByUuid(any())).thenReturn(token);

        mockMvc.perform(post("/review").principal(tokenPrincipal)
                .header("x-auth-token", "f7367061-ac52-4284-827e-3bc23d841dc1")
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void saveReviewReviewUserIsNotLoggedIn() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"ReviewControllerTest\"}";
        User user = new User();
        user.setEmail("guest");
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        user.setRoleList(roleList);
        Token token = new Token(user);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);
        when(authenticationService.getTokenForGuest()).thenReturn(token);
        mockMvc.perform(post("/review").principal(tokenPrincipal)
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andExpect(status().isUnauthorized());
    }

}