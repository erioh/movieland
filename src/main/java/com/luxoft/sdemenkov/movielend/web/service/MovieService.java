package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDaoImpl;
    @Autowired
    private GenreService genreService;
//    @Autowired
//    private CountryService countryService;

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = movieDaoImpl.getAllMovies();
        log.debug("Calling method getAllMovies, result = {}", allMovies);
        return allMovies;
    }

    public List<Movie> getThreeRandomMovies() {
        List<Movie> threeRandomMovies = movieDaoImpl.getThreeRandomMovies();
        threeRandomMovies = genreService.enrichMoviesByGenres(threeRandomMovies);
//        threeRandomMovies = countryService.ecrichMoviesByCountries(threeRandomMovies);
        log.debug("Calling method getThreeRandomMovies");
        return threeRandomMovies;
    }

    public void setMovieDaoImpl(MovieDao movieDaoImpl) {
        this.movieDaoImpl = movieDaoImpl;
    }
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
