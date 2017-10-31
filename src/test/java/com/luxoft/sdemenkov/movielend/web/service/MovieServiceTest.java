package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.CountryDaoImpl;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDaoImpl;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.MovieDaoImpl;
import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieServiceTest {
    private ApplicationContext context;
    private MovieService movieService;
    private CountryDao mockedCountryDao;
    private GenreDao mockedGenreDao;
    private MovieDao mockedMovieDao;
    private CountryService mockedCountryService;
    private GenreService mockedGenreService;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/test/resources/spring-test-config.xml");
        movieService = (MovieService) context.getBean("movieService");
        mockedCountryDao = mock(CountryDaoImpl.class);
        mockedGenreDao = mock(GenreDaoImpl.class);
        mockedMovieDao = mock(MovieDaoImpl.class);
        mockedCountryService = mock(CountryService.class);
        mockedGenreService = mock(GenreService.class);

    }

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
        movieService.setMovieDao(mockedMovieDao);

        // Test
        List<Movie> movieList = movieService.getAllMovies();
        Movie actualMovie = movieList.get(0);
        System.out.println(actualMovie);
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
        movieService.setMovieDao(mockedMovieDao);
        // Test
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();
        assertEquals(3, actualMovieList.size());
    }

}