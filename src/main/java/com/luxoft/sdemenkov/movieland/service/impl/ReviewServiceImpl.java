package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.ReviewDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ReviewServiceImpl implements ReviewService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ReviewDao reviewDao;

    @Override
    public List<Movie> enrichMoviesWithReviews(List<Movie> movieList) {
        List<Movie> enrichedMovieList = reviewDao.enrichMoviesWithReviews(movieList);
        logger.debug("ecrichMoviesWithReviews is executed");
        return enrichedMovieList;
    }
}
