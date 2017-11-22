package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;


public interface GenreService {
    List<Genre> getAll();

    void enrichMoviesWithGenres(List<Movie> movieList);
}
