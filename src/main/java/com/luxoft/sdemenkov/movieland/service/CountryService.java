package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface CountryService {
    void enrichMoviesWithCountries(List<Movie> movieList);

    List<Country> getAllCountries();
}
