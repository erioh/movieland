package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;
import java.util.Set;


public interface MovieService {
    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    List<Movie> getMoviesByGenre(int genreId);

    List<Movie> getMovieById(Set<Integer> movieIds);

    void save(Movie movie);

    void set(Movie movie);

    List<Movie> searchByTitle(String title);

    List<Movie> searchByTitle(String title, int pageNumber);
}
