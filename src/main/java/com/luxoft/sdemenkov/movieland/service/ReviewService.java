package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface ReviewService {
    void enrichMoviesWithReviews(List<Movie> movieList);
}
