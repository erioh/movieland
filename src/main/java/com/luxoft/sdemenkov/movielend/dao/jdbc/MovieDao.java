package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;


public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getThreeRandomMovies();

    int getCountOfMovies();

    List<Movie> getMovieListByIds(List<Integer> ids);

}
