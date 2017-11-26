package com.luxoft.sdemenkov.movieland.web.util;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;
import com.luxoft.sdemenkov.testutils.MovieDtoGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class MovieBuilderTest {
    @Test
    public void getOnlyMovies() throws Exception {
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().build();
        assertEquals(saveMovieDto.getNameNative(), movie.getNameNative());

    }

    @Test
    public void getMoviesWithGenre() throws Exception {
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        List<Integer> genreDtos = new ArrayList<>();
        genreDtos.add(1);
        saveMovieDto.setGenres(genreDtos);
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getGenres().build();
        assertEquals(1, movie.getGenreList().get(0).getId());

    }

    @Test
    public void getMoviesWithCountry() throws Exception {
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        List<Integer> countryDtos = new ArrayList<>();
        countryDtos.add(2);
        saveMovieDto.setCountries(countryDtos);
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getCountries().build();
        assertEquals(2, movie.getCountryList().get(0).getId());

    }


}