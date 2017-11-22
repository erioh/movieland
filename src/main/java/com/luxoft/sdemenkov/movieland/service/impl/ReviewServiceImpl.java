package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.ReviewDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Review;
import com.luxoft.sdemenkov.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ReviewDao reviewDao;

    @Override
    public void enrichMoviesWithReviews(List<Movie> movieList) {
        reviewDao.enrichMoviesWithReviews(movieList);
        logger.debug("enrichMoviesWithReviews is executed");
    }

    @Override
    public void saveReview(Review review, Movie movie) {
        reviewDao.saveReview(review, movie);
        logger.debug("Review with text {} is added by User {} to movie with id = {}", review.getText(), review.getUser(), movie.getId());
    }


}
