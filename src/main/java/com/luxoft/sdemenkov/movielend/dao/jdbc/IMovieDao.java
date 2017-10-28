package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.models.Movie;

import java.util.List;

public interface IMovieDao {
    List<Movie> getAllMovies();
}
