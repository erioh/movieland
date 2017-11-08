package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/1/2017.
 */
public interface GenreService {
    List<Genre> getAllGenres();

    List<Movie> enrichMoviesWithGenres(List<Movie> movieList);
}
