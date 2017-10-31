package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MovieDaoImplTest {
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/test/resources/spring-test-config.xml");
    }

    @Test
    public void getAllMovies() throws Exception {
        MovieDaoImpl movieDaoImpl = (MovieDaoImpl) context.getBean("movieDaoImpl");
        List<Movie> movieList = movieDaoImpl.getAllMovies();
        Movie expectedMovie = new Movie();
        expectedMovie.setId(15);
        expectedMovie.setNameRussian("Gladiator");
        expectedMovie.setNameNative("Gladiator");
        expectedMovie.setYearOfRelease(2000);
        expectedMovie.setRating(8.6);
        expectedMovie.setPrice(175.0);
        expectedMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        Movie actualMovie = null;
        for (Movie movie :
                movieList) {
            if (15 == movie.getId()) {
                actualMovie = movie;
            }
        }
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(), 0);
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());

    }

    @Test
    public void getCountOfMovies() throws Exception {
        MovieDaoImpl movieDaoImpl = (MovieDaoImpl) context.getBean("movieDaoImpl");
        assertEquals(25, movieDaoImpl.getCountOfMovies());
    }

    @Test
    public void getMovieListByIds() throws Exception {
        MovieDaoImpl movieDaoImpl = (MovieDaoImpl) context.getBean("movieDaoImpl");

        // Expected movie
        Movie expectedMovie = new Movie();
        expectedMovie.setId(15);
        expectedMovie.setNameRussian("Gladiator");
        expectedMovie.setNameNative("Gladiator");
        expectedMovie.setYearOfRelease(2000);
        expectedMovie.setRating(8.6);
        expectedMovie.setPrice(175.0);
        expectedMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");

        //Test
        List<Integer> ids = new ArrayList<>();
        ids.add(15);
        List<Movie> actualMovieList = movieDaoImpl.getMovieListByIds(ids);
        Movie actualMovie = actualMovieList.get(0);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice(), 0);
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());

    }

}