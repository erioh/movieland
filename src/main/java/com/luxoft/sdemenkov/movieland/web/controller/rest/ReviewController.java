package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;
import com.luxoft.sdemenkov.movieland.model.Token;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.role.Role;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> saveReview(TokenPrincipal principal, @RequestBody SaveReviewDTO saveReviewDTO) {

        logger.debug("saveReview. Object {} is receved as saveReviewDTO", saveReviewDTO);
        logger.debug("saveReview. Object {} is receved as tokenPrincipal", principal);
        Token token = principal.getToken();
        logger.debug("saveReview. Received token is", token);
        UUID uuid = token.getUuid();
        logger.debug("saveReview. Received uuid is", uuid);
        if (!"guest".equals(token.getUser().getEmail())) {
            if (token.getUser().getRoleList().contains(Role.USER)) {
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
                return new ResponseEntity<>(new ExceptionMessageDto("User is not allowed to add reviews"), HttpStatus.BAD_REQUEST);
            }

        } else {
            return new ResponseEntity<>(new ExceptionMessageDto("User is not logged in"), HttpStatus.BAD_REQUEST);
        }
    }


}
