package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class GenreServiceTest {
    @InjectMocks
    private GenreService genreService;
    @Mock
    private GenreDao mockedGenreDao;

    @Test
    public void getAllGenres() throws Exception {
        // Mocking expected result
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre());
        when(mockedGenreDao.getAllGenres()).thenReturn(genreList);
        assertEquals(1, genreService.getAllGenres().size());

    }

    @Test
    public void enrichMoviesByGenres() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        when(mockedGenreDao.enrichMoviesByGenres(anyList())).thenReturn(movieList);
        assertEquals(1, genreService.enrichMoviesByGenres(movieList).size());
    }

}