package com.luxoft.sdemenkov.movielend.web.service.impl;

import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.model.Movie;
import com.luxoft.sdemenkov.movielend.web.service.CountryService;
import com.luxoft.sdemenkov.movielend.web.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements com.luxoft.sdemenkov.movielend.web.service.MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = movieDao.getAllMovies();
        log.debug("Calling method getAllMovies, result = {}", allMovies);
        return allMovies;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> threeRandomMovies = movieDao.getThreeRandomMovies();
        threeRandomMovies = genreService.enrichMoviesByGenres(threeRandomMovies);
        threeRandomMovies = countryService.ecrichMoviesByCountries(threeRandomMovies);
        log.debug("Calling method getThreeRandomMovies");
        return threeRandomMovies;
    }

}
