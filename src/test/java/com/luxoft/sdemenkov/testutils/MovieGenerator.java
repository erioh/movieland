package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class MovieGenerator {
    public static Movie getMovieForTest() {
        Movie movie = new Movie();
        movie.setId(15);
        movie.setNameRussian("Gladiator");
        movie.setNameNative("Gladiator");
        movie.setYearOfRelease(2000);
        movie.setRating(8.6);
        movie.setPrice(new BigDecimal("175.0"));
        movie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        movie.setDescription("Description");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre(1, "Genre name"));
        movie.setGenreList(genreList);
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country(1, "Country name"));
        movie.setCountryList(countryList);
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(1, new User(1, "User name"), "Review text"));
        movie.setReviewList(reviewList);
        return movie;
    }

    public static Movie getMovieForTest(String property, String value) {
        Movie movie = getMovieForTest();
        switch (property) {
            case "rating":
                movie.setRating(Double.parseDouble(value));
                break;
            case "price":
                movie.setPrice(new BigDecimal(value));
                break;
            default:
                throw new IllegalArgumentException("Please check input property. Actual value = '" + property + "'");
        }
        return movie;
    }
}
