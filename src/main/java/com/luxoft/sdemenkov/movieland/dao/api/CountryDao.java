package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;


public interface CountryDao {
    List<Country> getCountryListByMovie(Movie movie);

    List<Movie> enrichMoviesWithCountries(List<Movie> movieList);

}
