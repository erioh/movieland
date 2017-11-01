package com.luxoft.sdemenkov.movieland.web.service;

import com.luxoft.sdemenkov.movieland.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.impl.MovieServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MovieServiceImplTest {
    @InjectMocks
    private MovieServiceImpl movieService;
    @Mock
    private MovieDao mockedMovieDao;
    @Mock
    private CountryService mockedCountryService;
    @Mock
    private GenreService mockedGenreService;

    @Test
    public void getAllMovies() throws Exception {

        // Creating expected Movie
        Movie expectedMovie = new Movie();
        expectedMovie.setId(15);
        expectedMovie.setNameRussian("Гладиатор");
        expectedMovie.setNameNative("Gladiator");
        expectedMovie.setYearOfRelease(2000);
        expectedMovie.setRating(8.6);
        expectedMovie.setPrice(175.0);
        expectedMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");


        // Mocking objects
        List<Movie> list = new ArrayList<>();
        list.add(expectedMovie);
        when(mockedMovieDao.getAllMovies()).thenReturn(list);
        // Test
        List<Movie> movieList = movieService.getAllMovies();
        Movie actualMovie = movieList.get(0);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(), 0);
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());
    }

    @Test
    public void getThreeRundomMovies() throws Exception {

        // Creating expected movies, countries and genres
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        movieList.add(new Movie());
        movieList.add(new Movie());
        //Mocking objects
        when(mockedMovieDao.getThreeRandomMovies()).thenReturn(movieList);
        when(mockedCountryService.ecrichMoviesByCountries(movieList)).thenReturn(movieList);
        when(mockedGenreService.enrichMoviesByGenres(movieList)).thenReturn(movieList);
        // Test
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();
        assertEquals(3, actualMovieList.size());
    }

}