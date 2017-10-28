package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.models.Genre;
import com.luxoft.sdemenkov.movielend.models.Movie;

import java.util.List;

/**
 * Created by sergeydemenkov on 28.10.17.
 */
public interface IGenreDao {

    List<Genre> getGenreListByMove(Movie movie);
    List<Genre> getAllGenres();

}
