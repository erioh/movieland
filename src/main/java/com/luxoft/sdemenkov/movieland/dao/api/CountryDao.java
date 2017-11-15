package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface CountryDao {
    List<Country> getCountryListByMovie(Movie movie);

    void enrichMoviesWithCountries(List<Movie> movieList);

    List<Country> getAllCountries();
}
