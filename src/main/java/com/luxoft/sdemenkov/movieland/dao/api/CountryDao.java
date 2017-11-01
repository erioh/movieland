package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 10/30/2017.
 */
public interface CountryDao {
    List<Country> getCountryListByMovie(Movie movie);

    List<Movie> enrichMoviesByCountries(List<Movie> movieList);

}
