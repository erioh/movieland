package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class GenreDaoImplTest {
    private ApplicationContext context;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/main/webapp/WEB-INF/spring/spring-test-config.xml");
    }

    @Test
    public void getGenreListByMove() throws Exception {
        GenreDaoImpl genreDaoImpl = (GenreDaoImpl) context.getBean("genreDaoImpl");
        Movie movie = new Movie();
        movie.setId(1);
        List<Genre> actualGenreList = genreDaoImpl.getGenreListByMove(movie);
        assertEquals(2, actualGenreList.size());
        assertEquals(1, actualGenreList.get(0).getId());
        assertEquals(2, actualGenreList.get(1).getId());
        assertEquals("drama", actualGenreList.get(0).getName());
        assertEquals("crime", actualGenreList.get(1).getName());

    }

    @Test
    public void getAllGenres() throws Exception {
        GenreDaoImpl genreDaoImpl = (GenreDaoImpl) context.getBean("genreDaoImpl");
        List<Genre> genreList = genreDaoImpl.getAllGenres();
        assertEquals(15, genreList.size());
    }

}