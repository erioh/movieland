package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/1/2017.
 */
public interface GenreService {
    List<Genre> getAllGenres();
    List<Movie> enrichMoviesByGenres(List<Movie> movieList);
}
