package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;

import java.util.List;


public interface ReviewDao {
    void enrichMoviesWithReviews(List<Movie> movieList);

    int saveReview(Review review, Movie movie);
}
