package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface GenreDao {
    List<Genre> getGenreListByMove(Movie movie);

    List<Genre> getAllGenres();

    void enrichMoviesWithGenres(List<Movie> movieList);
}
