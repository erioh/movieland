package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.SecurityService;
import com.luxoft.sdemenkov.movieland.web.response.TokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class SecurityTokenController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SecurityService securityService;
    private volatile Map<UUID, Long> logedUsers = new HashMap<>();

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User loginAsUser = securityService.loginAsUser(email, password);
        if (loginAsUser == null) {
            logger.error("Login or password are invalid. Used login = {}, used password = {}", email, password);
            return new ResponseEntity<String>("Login or password are invalid", HttpStatus.BAD_REQUEST);
        }
        UUID uuid = UUID.randomUUID();
        logger.debug("User {} with password {} is logged in. UUID = {}", email, password, uuid);
        Token token = new Token(uuid, loginAsUser.getNickname());
        logedUsers.put(token.getUuid(),System.currentTimeMillis());
        TokenDTO tokenDTO = new TokenDTO(token);
        return new ResponseEntity<TokenDTO>(tokenDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestParam String uuid) {
        if(!logedUsers.containsKey(UUID.fromString(uuid))) {
            logger.error("User with uuid = {} is not logged in", uuid);
            return new ResponseEntity<String>("User is not logged in", HttpStatus.BAD_REQUEST);
        }
        logedUsers.remove(UUID.fromString(uuid));
        return new ResponseEntity<String>("User is logged out", HttpStatus.OK);
    }


}
