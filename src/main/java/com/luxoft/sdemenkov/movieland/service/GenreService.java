package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface GenreService {
    List<Genre> getAllGenres();

    void enrichMoviesWithGenres(List<Movie> movieList);
}
