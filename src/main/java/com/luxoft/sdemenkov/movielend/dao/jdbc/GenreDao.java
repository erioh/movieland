package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 10/30/2017.
 */
public interface GenreDao {
    List<Genre> getGenreListByMove(Movie movie);

    List<Genre> getAllGenres();

    List<Movie> enrichMoviesByGenres(List<Movie> movieList);
}
