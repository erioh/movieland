package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;
import java.util.Set;


public interface MovieDao {
    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    int getCountOfMovies();

    List<Movie> getMovieListByIds(Set<Integer> ids);

    List<Movie> getMoviesByGenre(int genreId);

    void save(Movie movie);

    void set(Movie movie);

    List<Movie> searchByTitle(String title);

    List<Movie> searchByTitle(String title, int pageNumber, int moviesPerPage);
}
