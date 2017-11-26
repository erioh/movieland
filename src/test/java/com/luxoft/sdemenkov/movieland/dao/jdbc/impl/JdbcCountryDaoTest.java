package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;
import com.luxoft.sdemenkov.movieland.web.util.MovieBuilder;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcCountryDaoTest {

    @Autowired
    private CountryDao countryDao;

    @Test
    public void getCountryListByMovie() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        movie.setId(6);
        List<Country> actualCountryList = countryDao.getCountryListByMovie(movie);
        assertEquals(2, actualCountryList.size());
        assertEquals(1, actualCountryList.get(0).getId());
        assertEquals(3, actualCountryList.get(1).getId());
        assertEquals("США", actualCountryList.get(0).getName());
        assertEquals("Великобритания", actualCountryList.get(1).getName());

    }

    @Test
    public void enrichMoviesByGenres() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        countryDao.enrichMoviesWithCountries(movies);
        for (Movie movie1 : movies) {
            assertNotEquals(0, movie1.getCountryList().size());
        }
    }

    @Test
    public void getAll() throws Exception {
        List<Country> countryList = countryDao.getAll();
        assertEquals(7, countryList.size());
    }

    @Test
    @Transactional
    public void mapMoviesCountry() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        int[] counts = countryDao.mapMoviesCountry(movie);
        assertEquals(1, counts[0]);
    }

    @Test
    @Transactional
    public void mapMoviesGenrenNullGenres() throws Exception {
        SaveMovieDto saveMovieDto = new SaveMovieDto();
        Movie movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getCountries().getGenres().build();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        int[] counts = countryDao.mapMoviesCountry(movie);
        assertEquals(0, counts[0]);
    }

    @Test
    @Transactional
    public void removeMappedCountries() throws Exception {
        Movie movie = new Movie();
        movie.setId(3);
        int count = countryDao.removeMappedCountries(movie);
        assertEquals(1, count);
    }


}