package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by sergeydemenkov on 04.11.17.
 */
public interface ReviewService {
    void enrichMoviesWithReviews(List<Movie> movieList);
}
