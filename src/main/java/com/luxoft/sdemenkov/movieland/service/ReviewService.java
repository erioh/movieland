package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;

import java.util.List;


public interface ReviewService {
    void enrichMoviesWithReviews(List<Movie> movieList);

    void saveReview(Review review, Movie movie);
}
