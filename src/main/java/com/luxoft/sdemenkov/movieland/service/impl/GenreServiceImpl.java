package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sergeydemenkov on 28.10.17.
 */

@Service
public class GenreServiceImpl implements GenreService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenreDao genreDao;


    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genreList = genreDao.getAllGenres();
        log.debug("Calling method getAllMovies");
        return genreList;

    }

    @Override
    public List<Movie> enrichMoviesByGenres(List<Movie> movieList) {
        List<Movie> enrichedMovieList = genreDao.enrichMoviesByGenres(movieList);
        log.debug("ecrichMoviesByGenres is executed");
        return enrichedMovieList;
    }

}
