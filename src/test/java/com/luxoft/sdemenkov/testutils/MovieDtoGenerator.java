package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;


public class MovieDtoGenerator {
    public static SaveMovieDto getMovieForTest() {
        SaveMovieDto movie = new SaveMovieDto();
        movie.setId(15);
        movie.setNameRussian("Гладиатор");
        movie.setNameNative("Gladiator");
        movie.setYearOfRelease(2000);
        movie.setRating(8.6);
        movie.setPrice(175.0);
        movie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        movie.setDescription("Description");
        return movie;
    }

    public static SaveMovieDto getMovieForTest(String property, String value) {
        SaveMovieDto movie = getMovieForTest();
        switch (property) {
            case "rating":
                movie.setRating(Double.parseDouble(value));
                break;
            case "price":
                movie.setPrice(Double.parseDouble(value));
                break;
            default:
                throw new IllegalArgumentException("Please check input property. Actual value = '" + property + "'");
        }
        return movie;
    }
}
