package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcGenreDaoTest {

    @Autowired
    private GenreDao genreDao;

    @Test
    public void getGenreListByMove() throws Exception {

        Movie movie = new Movie();
        movie.setId(1);
        List<Genre> actualGenreList = genreDao.getGenreListByMove(movie);
        assertEquals(2, actualGenreList.size());
        assertEquals(1, actualGenreList.get(0).getId());
        assertEquals(2, actualGenreList.get(1).getId());
        assertEquals("драма", actualGenreList.get(0).getName());
        assertEquals("криминал", actualGenreList.get(1).getName());
    }

    @Test
    public void getAll() throws Exception {
        List<Genre> genreList = genreDao.getAll();
        assertEquals(15, genreList.size());
    }

    @Test
    public void enrichMoviesByGenres() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        genreDao.enrichMoviesWithGenres(movies);
        assertEquals(2, movies.get(0).getGenreList().size());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test() throws Exception {
        List<Genre> genreListCached1 = genreDao.getAll();
        genreListCached1.set(0, new Genre(0, "Main"));
        List<Genre> genreListCached2 = genreDao.getAll();

    }

    @Test
    @Transactional
    public void mapMoviesGenre() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        List<Movie> movies = new ArrayList<>();
        movies.add(movie);
        genreDao.mapMoviesGenre(movie);
    }


}