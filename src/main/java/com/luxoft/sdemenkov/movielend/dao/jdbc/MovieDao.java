package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 10/30/2017.
 */
public interface MovieDao {
    List<Movie> getAllMovies();

}
