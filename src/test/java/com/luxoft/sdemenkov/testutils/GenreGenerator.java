package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.model.business.Genre;


public class GenreGenerator {
    public static Genre getGenreForTest() {
        return new Genre(1, "Name");
    }
}
