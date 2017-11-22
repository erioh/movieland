package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;


public interface GenreDao {
    List<Genre> getGenreListByMove(Movie movie);

    List<Genre> getAll();

    void enrichMoviesWithGenres(List<Movie> movieList);
}
