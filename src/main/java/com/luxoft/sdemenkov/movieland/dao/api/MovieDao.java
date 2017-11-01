package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 10/30/2017.
 */
public interface MovieDao {
    List<Movie> getAllMovies();

    List<Movie> getThreeRandomMovies();

    int getCountOfMovies();

    List<Movie> getMovieListByIds(List<Integer> ids);

    List<Movie> getMoviesByGenre(int genreId);

}
