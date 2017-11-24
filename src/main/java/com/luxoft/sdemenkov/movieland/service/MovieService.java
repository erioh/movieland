package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;
import java.util.Set;


public interface MovieService {
    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    List<Movie> getMoviesByGenre(int genreId);

    List<Movie> getMovieById(Set<Integer> movieIds);

    Movie save(Movie movie);

    Movie set(Movie movie);
}
