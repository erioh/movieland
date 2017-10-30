package com.luxoft.sdemenkov.movielend.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.CountryDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.MovieDao;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class MovieService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;
    @Autowired
    private CountryDao countryDao;
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private Random randomGenerator;

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = movieDao.getAllMovies();
        log.debug("Calling method getAllMovies, result = {}", allMovies);
        return allMovies;
    }

    public List<Movie> getThreeRundomMovies() {
        List<Movie> movieList = movieDao.getAllMovies();
        List<Movie> threeRundomMovies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = randomGenerator.nextInt(movieList.size());
            Movie randomMovie = movieList.get(index);
            randomMovie.setCountryList(countryDao.getCountryListByMovie(randomMovie));
            randomMovie.setGenreList(genreDao.getGenreListByMove(randomMovie));
            threeRundomMovies.add(randomMovie);
        }
        log.debug("Calling method getThreeRundomMovies");
        return threeRundomMovies;

    }

    public MovieDao getMovieDao() {
        return movieDao;
    }

    public void setMovieDao(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public CountryDao getCountryDao() {
        return countryDao;
    }

    public void setCountryDao(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    public GenreDao getGenreDao() {
        return genreDao;
    }

    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }
}
