package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryService {
    @Autowired
    private CountryDao countryDao;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public List<Movie> ecrichMoviesByCountries(List<Movie> movieList) {
        List<Movie> enrichedMovieList = countryDao.enrichMoviesByCountries(movieList);
        log.debug("ecrichMoviesByCountries is executed");
        return enrichedMovieList;
    }
}
