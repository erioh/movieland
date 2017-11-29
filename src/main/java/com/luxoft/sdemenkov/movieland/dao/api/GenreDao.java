package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;


public interface GenreDao {
    List<Genre> getGenreListByMovie(Movie movie);

    List<Genre> getAll();

    void enrichMoviesWithGenres(List<Movie> movieList);

    int[] mapMoviesGenre(Movie movie);

    int removeMappedGenres(Movie movie);
}
