package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;
import java.util.Set;


public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getThreeRandomMovies();

    int getCountOfMovies();

    List<Movie> getMovieListByIds(Set<Integer> ids);

    List<Movie> getMoviesByGenre(int genreId);

}
