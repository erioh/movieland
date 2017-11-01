package com.luxoft.sdemenkov.movieland.dao.jdbc;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 10/30/2017.
 */
public interface GenreDao {
    List<Genre> getGenreListByMove(Movie movie);

    List<Genre> getAllGenres();

    List<Movie> enrichMoviesByGenres(List<Movie> movieList);
}
