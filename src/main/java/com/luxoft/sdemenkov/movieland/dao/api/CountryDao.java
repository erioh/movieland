package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;


public interface CountryDao {
    List<Country> getCountryListByMovie(Movie movie);

    void enrichMoviesWithCountries(List<Movie> movieList);

    List<Country> getAll();

    void mapMoviesCountry(Movie movie);
}
