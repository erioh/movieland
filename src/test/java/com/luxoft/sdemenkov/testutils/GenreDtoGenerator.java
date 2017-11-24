package com.luxoft.sdemenkov.testutils;

import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.web.dto.GenreDto;


public class GenreDtoGenerator {
    public static GenreDto getGenreForTest() {
        return new GenreDto(new Genre(1, "Name"));
    }
}
