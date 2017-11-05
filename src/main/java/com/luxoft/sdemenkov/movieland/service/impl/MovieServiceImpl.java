package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> allMovies = movieDao.getAllMovies();
        log.debug("Calling method getAllMovies, result = {}", allMovies);
        return allMovies;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> threeRandomMovies = movieDao.getThreeRandomMovies();
        threeRandomMovies = genreService.enrichMoviesWithGenres(threeRandomMovies);
        threeRandomMovies = countryService.ecrichMoviesWithCountries(threeRandomMovies);
        log.debug("Calling method getThreeRandomMovies");
        return threeRandomMovies;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> movieList = movieDao.getMoviesByGenre(genreId);
        log.debug("Method getMoviesByGenre is called for genreId = {} with result: {}", genreId, movieList);
        return movieList;
    }

    @Override
    public List<Movie> getMovieById(Set<Integer> movieIds) {
        List<Movie> movieList = movieDao.getMovieListByIds(movieIds);
        movieList = genreService.enrichMoviesWithGenres(movieList);
        movieList = countryService.ecrichMoviesWithCountries(movieList);
        movieList = reviewService.enrichMoviesWithReviews(movieList);
        log.debug("Method getMovieById is called for iss = {} with result = {}", movieIds, movieList);
        return movieList;
    }
}
