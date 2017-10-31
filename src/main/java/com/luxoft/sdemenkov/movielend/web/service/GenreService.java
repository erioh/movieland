package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GenreService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenreDao genreDao;


    public List<Genre> getAllGenres() {
        List<Genre> genreList = genreDao.getAllGenres();
        log.debug("Calling method getAllMovies");
        return genreList;

    }

    public List<Movie> enrichMoviesByGenres(List<Movie> movieList) {
        List<Movie> enrichedMovieList = genreDao.enrichMoviesByGenres(movieList);
        log.debug("ecrichMoviesByGenres is executed");
        return enrichedMovieList;
    }

}
