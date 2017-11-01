package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.model.Country;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcCountryDaoTest {

    @Autowired
    CountryDao countryDao;

    @Test
    public void getCountryListByMovie() throws Exception {
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
        Movie movie = getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        movies = countryDao.enrichMoviesByCountries(movies);
        for (Movie movie1 : movies) {
            assertNotEquals(0, movie1.getCountryList().size());
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