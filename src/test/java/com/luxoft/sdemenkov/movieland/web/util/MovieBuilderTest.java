package com.luxoft.sdemenkov.movieland.web.util;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.CountryDto;
import com.luxoft.sdemenkov.movieland.web.dto.GenreDto;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;
import com.luxoft.sdemenkov.testutils.GenreDtoGenerator;
import com.luxoft.sdemenkov.testutils.MovieDtoGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class MovieBuilderTest {
    @Test
    public void getOnlyMovies() throws Exception{
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().build();
        assertEquals(saveMovieDto.getNameNative(), movie.getNameNative());

    }

    @Test
    public void getMoviesWithGenre() throws Exception{
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        List<GenreDto> genreDtos = new ArrayList<>();
        genreDtos.add(GenreDtoGenerator.getGenreForTest());
        saveMovieDto.setGenres(genreDtos);
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getGenres().build();
        assertEquals("Name", movie.getGenreList().get(0).getName());

    }

    @Test
    public void getMoviesWithCountry() throws Exception{
        Movie movie;
        SaveMovieDto saveMovieDto = MovieDtoGenerator.getMovieForTest();
        List<CountryDto> countryDtos = new ArrayList<>();
        countryDtos.add(new CountryDto(new Country(1, "Country")));
        saveMovieDto.setCountries(countryDtos);
        movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getCountries().build();
        assertEquals("Country", movie.getCountryList().get(0).getName());

    }


}