package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CountryDaoImplTest {
    private ApplicationContext context;


    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/test/resources/spring-test-config.xml");
    }

    @Test
    public void getCountryListByMovie() throws Exception {
        CountryDao countryDao = (CountryDao) context.getBean("countryDaoImpl");
        Movie movie = new Movie();
        movie.setId(6);
        List<Country> actualCountryList = countryDao.getCountryListByMovie(movie);
        assertEquals(2, actualCountryList.size());
        assertEquals(1, actualCountryList.get(0).getId());
        assertEquals(3, actualCountryList.get(1).getId());
        assertEquals("USA", actualCountryList.get(0).getName());
        assertEquals("Great Britain", actualCountryList.get(1).getName());

    }

    @Test
    public void enrichMoviesByGenres() throws Exception {
        CountryDao countryDao = (CountryDao) context.getBean("countryDaoImpl");
        Movie movie = getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies = countryDao.enrichMoviesByCountries(movies);
        for (Movie movie1 : movies) {
            System.out.println(movie1);
        }
    }


    private Movie getMovieForTest() throws Exception {
        Movie expectedMovie = new Movie();
        expectedMovie.setId(15);
        expectedMovie.setNameRussian("Gladiator");
        expectedMovie.setNameNative("Gladiator");
        expectedMovie.setYearOfRelease(2000);
        expectedMovie.setRating(8.6);
        expectedMovie.setPrice(175.0);
        expectedMovie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        return expectedMovie;
    }

}