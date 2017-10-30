package com.luxoft.sdemenkov.movielend.web.service;

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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieServiceTest {
    private ApplicationContext context;
    private MovieService movieService;
    private CountryDaoImpl mockedCountryDaoImpl;
    private GenreDaoImpl mockedGenreDaoImpl;
    private MovieDaoImpl mockedMovieDaoImpl;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/test/resources/spring-test-config.xml");
        movieService = (MovieService) context.getBean("movieService");
        mockedCountryDaoImpl = mock(CountryDaoImpl.class);
        mockedGenreDaoImpl = mock(GenreDaoImpl.class);
        mockedMovieDaoImpl = mock(MovieDaoImpl.class);

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
        when(mockedMovieDaoImpl.getAllMovies()).thenReturn(list);
        movieService.setMovieDaoImpl(mockedMovieDaoImpl);

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
        Country country = new Country();
        List<Country> countryList = new ArrayList<>();
        countryList.add(country);

        Genre genre = new Genre();
        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre);

        Movie movie = new Movie();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        //Mocking objects
        when(mockedMovieDaoImpl.getAllMovies()).thenReturn(movieList);
        when(mockedCountryDaoImpl.getCountryListByMovie((Movie) any())).thenReturn(countryList);
        when(mockedGenreDaoImpl.getGenreListByMove((Movie) any())).thenReturn(genreList);
        movieService.setMovieDaoImpl(mockedMovieDaoImpl);
        movieService.setGenreDaoImpl(mockedGenreDaoImpl);
        movieService.setCountryDaoImpl(mockedCountryDaoImpl);

        // Test
        List<Movie> actualMovieList = movieService.getThreeRandomMovies();
        assertEquals(3, actualMovieList.size());
        assertNotEquals(0, actualMovieList.get(0).getCountryList().size());
        assertNotEquals(0, actualMovieList.get(0).getGenreList().size());

    }

}