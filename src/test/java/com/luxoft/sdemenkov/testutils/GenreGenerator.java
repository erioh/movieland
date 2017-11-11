package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.model.Genre;


public class GenreGenerator {
    public static Genre getGenreForTest() {
        Genre genre = new Genre(1, "Name");
        return genre;
    }
}
