
package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.model.User;
import com.luxoft.sdemenkov.movieland.service.UserService;
import com.luxoft.sdemenkov.movieland.web.response.StringDto;
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

import java.util.UUID;

@RestController
@RequestMapping("/")
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Token token;
        try {
            token = userService.login(email, password);
        } catch (RuntimeException e) {
            return new ResponseEntity<StringDto>(new StringDto(e.getMessage()),HttpStatus.BAD_REQUEST);
        }
        TokenDTO tokenDTO = new TokenDTO(token);
        logger.debug("Getting of user with email {} and password {} returns {}", email, password, token);
        return new ResponseEntity<TokenDTO>(tokenDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestParam String uuid) {
        if(!userService.isAlive(UUID.fromString(uuid))) {
            logger.error("User with uuid = {} is not logged in", uuid);
            return new ResponseEntity<StringDto>(new StringDto("User is not logged in!"),HttpStatus.BAD_REQUEST);
        }
        userService.logout(UUID.fromString(uuid));
        return new ResponseEntity<StringDto>(new StringDto("User is logged out"), HttpStatus.OK);
    }


}
