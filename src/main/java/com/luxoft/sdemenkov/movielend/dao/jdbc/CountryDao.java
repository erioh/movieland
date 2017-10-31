package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;


public interface CountryDao {
    List<Country> getCountryListByMovie(Movie movie);

    List<Movie> enrichMoviesByCountries(List<Movie> movieList);

}
