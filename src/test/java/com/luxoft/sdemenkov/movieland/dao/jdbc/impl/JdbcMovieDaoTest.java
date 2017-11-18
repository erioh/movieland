package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcMovieDaoTest {

    @Autowired
    private
    MovieDao movieDao;

    @Test
    public void getAllMovies() throws Exception {
        List<Movie> movieList = movieDao.getAllMovies();
        Movie expectedMovie = MovieGenerator.getMovieForTest();
        Movie actualMovie = null;
        for (Movie movie :
                movieList) {
            if (15 == movie.getId()) {
                actualMovie = movie;
            }
        }
        assert actualMovie != null;
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice());
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());

    }

    @Test
    public void getCountOfMovies() throws Exception {
        assertEquals(25, movieDao.getCountOfMovies());
    }

    @Test
    public void getMovieListByIds() throws Exception {

        // Expected movie
        Movie expectedMovie = MovieGenerator.getMovieForTest();
        //Main
        Set<Integer> ids = new HashSet<>();
        ids.add(15);
        List<Movie> actualMovieList = movieDao.getMovieListByIds(ids);
        Movie actualMovie = actualMovieList.get(0);
        assertEquals(expectedMovie.getId(), actualMovie.getId());
        assertEquals(expectedMovie.getNameRussian(), actualMovie.getNameRussian());
        assertEquals(expectedMovie.getNameNative(), actualMovie.getNameNative());
        assertEquals(expectedMovie.getYearOfRelease(), actualMovie.getYearOfRelease());
        assertEquals(expectedMovie.getRating(), actualMovie.getRating(), 0);
        assertEquals(expectedMovie.getPrice(), actualMovie.getPrice());
        assertEquals(expectedMovie.getPicturePath(), actualMovie.getPicturePath());

    }

    @Test
    public void getThreeRandomMovies() throws Exception {
        List<Movie> movieList = movieDao.getThreeRandomMovies();
        assertEquals(3, movieList.size());
    }


    @Test
    public void getMoviesByGenre() throws Exception {
        List<Movie> movieList = movieDao.getMoviesByGenre(1);
        assertEquals(16, movieList.size());
    }

}