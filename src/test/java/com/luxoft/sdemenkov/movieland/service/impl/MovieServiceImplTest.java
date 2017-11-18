package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
        Movie expectedMovie = MovieGenerator.getMovieForTest();

        // Mocking objects
        List<Movie> list = new ArrayList<>();
        list.add(expectedMovie);
        when(mockedMovieDao.getAllMovies()).thenReturn(list);
        // Main
        List<Movie> movieList = movieService.getAllMovies();
        Movie actualMovie = movieList.get(0);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertTrue(expectedMovie.getPrice().equals(actualMovie.getPrice()));
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());
    }

    @Test
    public void getThreeRandomMovies() throws Exception {

        // Creating expected movies, countries and genres
        List<Movie> movieList = new ArrayList<>();
        movieList.add(MovieGenerator.getMovieForTest());
        movieList.add(MovieGenerator.getMovieForTest());
        movieList.add(MovieGenerator.getMovieForTest());
        //Mocking objects
        when(mockedMovieDao.getThreeRandomMovies()).thenReturn(movieList);
        // Main
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();
        assertEquals(3, actualMovieList.size());
    }

    @Test
    public void getMoviesByGenre() throws Exception {

        // Creating expected movies, countries and genres
        List<Movie> movieList = new ArrayList<>();
        movieList.add(MovieGenerator.getMovieForTest());
        movieList.add(MovieGenerator.getMovieForTest());
        movieList.add(MovieGenerator.getMovieForTest());

        //Mocking objects
        when(mockedMovieDao.getMoviesByGenre(1)).thenReturn(movieList);

        // Main
        List<Movie> actualResult = movieService.getMoviesByGenre(1);
        assertEquals(3, actualResult.size());
    }


}