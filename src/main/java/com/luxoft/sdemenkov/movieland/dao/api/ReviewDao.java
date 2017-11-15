package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface ReviewDao {
    void enrichMoviesWithReviews(List<Movie> movieList);
}
