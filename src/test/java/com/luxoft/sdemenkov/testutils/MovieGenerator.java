package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/3/2017.
 */
public class MovieGenerator {
    public static Movie getMovieForTest() {
        Movie movie = new Movie();
        movie.setId(15);
        movie.setNameRussian("Gladiator");
        movie.setNameNative("Gladiator");
        movie.setYearOfRelease(2000);
        movie.setRating(8.6);
        movie.setPrice(175.0);
        movie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre());
        movie.setGenreList(genreList);
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country());
        movie.setCountryList(countryList);
        return movie;
    }

    public static Movie getMovieForTest(String property, String value) {
        Movie movie = getMovieForTest();
        switch (property) {
            case "rating" :
                movie.setRating(Double.parseDouble(value));
                break;
            case "price" :
                movie.setPrice(Double.parseDouble(value));
                break;
            default :
                throw new IllegalArgumentException("Please check input property. Actual value = '"+property+"'");
        }
        return movie;
    }
}
