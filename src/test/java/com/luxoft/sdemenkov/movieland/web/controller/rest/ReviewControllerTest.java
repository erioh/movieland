package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.service.impl.AuthenticationServiceImpl;
import com.luxoft.sdemenkov.movieland.service.impl.ReviewServiceImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/test-rest-web-controller-servlet.xml",  "classpath:/spring-test-config.xml"})
@WebAppConfiguration()
public class ReviewControllerTest {

    private MockMvc mockMvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void saveReviewReviewIsAdded() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"Test\"}";
        User user = new User();
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        user.setRoleList(roleList);
        UUID uuid = UUID.randomUUID();
        Token token = new Token(user);
        Map<UUID, Token> tokenMap = new HashMap<>();
        tokenMap.put(uuid,token);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);
        mockMvc.perform(post("/login").param("email", "ronald.reynolds66@example.com").param("password", "paco")).andExpect(status().isOk());
        mockMvc.perform(post("/review").principal(tokenPrincipal)
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andExpect(status().isOk()
                );
    }

    @Test
    public void saveReviewReviewUserIsNotAllowedToAddReviews() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"Test\"}";
        User user = new User();
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.ADMIN);
        user.setRoleList(roleList);
        Token token = new Token(user);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);

        mockMvc.perform(post("/review").principal(tokenPrincipal)
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    public void saveReviewReviewUserIsNotLoggedIn() throws Exception {
        String body = "{\"movieId\":1,\"text\":\"Test\"}";
        User user = new User();
        user.setEmail("guest");
        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);
        user.setRoleList(roleList);
        Token token = new Token(user);
        TokenPrincipal tokenPrincipal = new TokenPrincipal(token);

        mockMvc.perform(post("/review").principal(tokenPrincipal)
                .contentType("application/json;charset=UTF-8")
                .content(body))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}