package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface GenreDao {
    List<Genre> getGenreListByMove(Movie movie);

    List<Genre> getAllGenres();

    List<Movie> enrichMoviesWithGenres(List<Movie> movieList);
}
