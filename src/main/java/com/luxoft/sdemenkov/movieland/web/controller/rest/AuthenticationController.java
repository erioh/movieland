package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.web.response.ExceptionMessageDto;
import com.luxoft.sdemenkov.movieland.web.response.TokenDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/")
public class AuthenticationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Token token;
        try {
            token = authenticationService.login(email, password);
        } catch (RuntimeException e) {
            return new ResponseEntity<ExceptionMessageDto>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        TokenDTO tokenDTO = new TokenDTO(token);
        logger.debug("Getting of user with email {} and password {} returns {}", email, password, token);
        return new ResponseEntity<TokenDTO>(tokenDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.DELETE)
    public ResponseEntity<?> logout(@RequestHeader("x-auth-token") String uuid) {
        authenticationService.logout(UUID.fromString(uuid));
        return new ResponseEntity<ExceptionMessageDto>(new ExceptionMessageDto("User is logged out"), HttpStatus.OK);
    }


}
