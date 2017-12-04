package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.service.*;
import com.luxoft.sdemenkov.movieland.web.exception.WrongRateValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class MovieServiceImpl implements MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${web.movie.search.result.movies.per.page}")
    private int moviesPerPage;

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ReviewService reviewService;

    @Override
    public List<Movie> getAll() {
        List<Movie> allMovies = movieDao.getAll();
        log.debug("Calling method getAll, result = {}", allMovies);
        return allMovies;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Movie> threeRandomMovies = movieDao.getThreeRandomMovies();
        genreService.enrichMoviesWithGenres(threeRandomMovies);
        countryService.enrichMoviesWithCountries(threeRandomMovies);
        for (Movie movie : threeRandomMovies) {
            movieDao.enrichMovieWithActualRates(movie);
        }
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
        genreService.enrichMoviesWithGenres(movieList);
        countryService.enrichMoviesWithCountries(movieList);
        reviewService.enrichMoviesWithReviews(movieList);
        Movie movie = movieDao.enrichMovieWithActualRates(movieList.get(0));
        List<Movie> movieForReturn = new ArrayList<>();
        movieForReturn.add(movie);
        log.debug("Method getMovieById is called for iss = {} with result = {}", movieIds, movieList);
        return movieForReturn;
    }

    @Override
    @Transactional
    public void save(Movie movie) {
        movieDao.save(movie);
        genreService.mapMoviesGenre(movie);
        countryService.mapMoviesCountry(movie);
        log.debug("save. Movie {} is successfully  saved", movie);
    }

    @Override
    @Transactional
    public void update(Movie movie) {
        movieDao.update(movie);
        genreService.mapMoviesGenre(movie);
        countryService.mapMoviesCountry(movie);
        log.debug("update. Movie {} is successfully  updated", movie);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return movieDao.searchByTitle(title);
    }

    @Override
    public List<Movie> searchByTitle(String title, int pageNumber) {
        if (pageNumber < 0) {
            throw new RuntimeException("Page number " + pageNumber + " is not valid");
        }
        return movieDao.searchByTitle(title, pageNumber, moviesPerPage);
    }

    @Override
    public void saveRate(Rate rate) {
        validateRate(rate);
        movieDao.saveRate(rate);
    }

    public void validateRate(Rate rate) {
        double rating = rate.getRating();
        if (rating < 1 || rating > 10) {
            throw new WrongRateValueException(rating);
        }
    }
}
