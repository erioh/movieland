package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;
import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.service.AuthenticationService;
import com.luxoft.sdemenkov.movieland.service.impl.ReviewServiceImpl;
import com.luxoft.sdemenkov.movieland.web.request.SaveReviewDTO;
import com.luxoft.sdemenkov.movieland.web.response.ExceptionMessageDto;
import com.luxoft.sdemenkov.movieland.web.response.ResponseMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveReview(Principal principal, @RequestBody SaveReviewDTO saveReviewDTO) {

        logger.debug("saveReview. Object {} is receved as saveReviewDTO", saveReviewDTO);
        TokenPrincipal tokenPrincipal = (TokenPrincipal) principal;
        logger.debug("saveReview. Object {} is receved as tokenPrincipal", principal);
        Token token = tokenPrincipal.getToken();
        logger.debug("saveReview. Received token is", token);
        UUID uuid = token.getUuid();
        logger.debug("saveReview. Received uuid is", uuid);
        if (authenticationService.isAlive(uuid)) {
            logger.debug("saveReview. is started");
            Review review = new Review()
                    .setText(saveReviewDTO.getText())
                    .setUser(token.getUser());
            logger.debug("saveReview. Review {} is ready", review);
            Movie movie = new Movie()
                    .setId(saveReviewDTO.getMovieId());
            reviewService.saveReview(review, movie);
            return new ResponseEntity<>(new ResponseMessageDto("Review is saved"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ExceptionMessageDto("user is not logged in"), HttpStatus.OK);
        }
    }


}
