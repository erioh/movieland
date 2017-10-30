package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDaoImpl;
import com.luxoft.sdemenkov.movielend.model.Genre;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by sergeydemenkov on 28.10.17.
 */
public class GenreServiceTest {

    private ApplicationContext context;
    private GenreService genreService;
    private GenreDaoImpl mockedGenreDaoImpl;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/main/webapp/WEB-INF/spring/spring-test-config.xml");
        genreService = (GenreService) context.getBean("genreService");
        mockedGenreDaoImpl = mock(GenreDaoImpl.class);
    }

    @Test
    public void getAllGenres() throws Exception {
        // Mocking expected result
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre());
        when(mockedGenreDaoImpl.getAllGenres()).thenReturn(genreList);
        genreService.setGenreDaoImpl(mockedGenreDaoImpl);
        assertEquals(1, genreService.getAllGenres().size());

    }

}