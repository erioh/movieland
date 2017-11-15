package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;
import java.util.Set;


public interface MovieService {
    List<Movie> getAllMovies();

    List<Movie> getThreeRandomMovies();

    List<Movie> getMoviesByGenre(int genreId);

    List<Movie> getMovieById(Set<Integer> movieIds);
}
