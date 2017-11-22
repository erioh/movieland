package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CountryServiceImpl implements CountryService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private CountryDao countryDao;

    @Override
    public void enrichMoviesWithCountries(List<Movie> movieList) {
        countryDao.enrichMoviesWithCountries(movieList);
        log.debug("enrichMoviesWithCountries is executed");
    }

    @Override
    public List<Country> getAll() {
        List<Country> countryList = countryDao.getAll();
        log.debug("getAll is executed");
        return countryList;
    }
}
