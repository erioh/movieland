package com.luxoft.sdemenkov.movielend.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.CountryDaoImpl;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDaoImpl;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.MovieDaoImpl;
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
    private MovieDao movieDaoImpl;
    @Autowired
    private CountryDao countryDaoImpl;
    @Autowired
    private GenreDao genreDaoImpl;
    @Autowired
    private Random randomGenerator;

    public List<Movie> getAllMovies() {
        List<Movie> allMovies = movieDaoImpl.getAllMovies();
        log.debug("Calling method getAllMovies, result = {}", allMovies);
        return allMovies;
    }

    public List<Movie> getThreeRundomMovies() {
        List<Movie> movieList = movieDaoImpl.getAllMovies();
        List<Movie> threeRundomMovies = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int index = randomGenerator.nextInt(movieList.size());
            Movie randomMovie = movieList.get(index);
            randomMovie.setCountryList(countryDaoImpl.getCountryListByMovie(randomMovie));
            randomMovie.setGenreList(genreDaoImpl.getGenreListByMove(randomMovie));
            threeRundomMovies.add(randomMovie);
        }
        log.debug("Calling method getThreeRundomMovies");
        return threeRundomMovies;

    }

    public MovieDao getMovieDaoImpl() {
        return movieDaoImpl;
    }

    public void setMovieDaoImpl(MovieDao movieDaoImpl) {
        this.movieDaoImpl = movieDaoImpl;
    }

    public CountryDao getCountryDaoImpl() {
        return countryDaoImpl;
    }

    public void setCountryDaoImpl(CountryDao countryDaoImpl) {
        this.countryDaoImpl = countryDaoImpl;
    }

    public GenreDao getGenreDaoImpl() {
        return genreDaoImpl;
    }

    public void setGenreDaoImpl(GenreDao genreDaoImpl) {
        this.genreDaoImpl = genreDaoImpl;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }
}
