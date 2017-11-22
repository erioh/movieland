package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Review;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.Protected;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import com.luxoft.sdemenkov.movieland.service.impl.ReviewServiceImpl;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveReviewDto;
import com.luxoft.sdemenkov.movieland.web.dto.response.ResponseMessageDto;
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

import java.util.UUID;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewServiceImpl reviewService;

    @Protected(protectedBy = Role.USER)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveReview(TokenPrincipal principal, @RequestBody SaveReviewDto saveReviewDto) {

        logger.debug("saveReview. Object {} is received as saveReviewDto", saveReviewDto);
        logger.debug("saveReview. Object {} is received as tokenPrincipal", principal);

        Token token = principal.getToken().get();

        logger.debug("saveReview. Received token is", token);

        UUID uuid = token.getUuid();

        logger.debug("saveReview. Received uuid is", uuid);
        logger.debug("saveReview. is started");

        Review review = new Review()
                .setText(saveReviewDto.getText())
                .setUser(token.getUser());

        logger.debug("saveReview. Review {} is ready", review);

        Movie movie = new Movie()
                .setId(saveReviewDto.getMovieId());
        reviewService.saveReview(review, movie);

        return new ResponseEntity<>(new ResponseMessageDto("Review is saved"), HttpStatus.OK);
    }


}
