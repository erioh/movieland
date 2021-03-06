package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.testutils.GenreGenerator;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GenreServiceImplTest {
    @InjectMocks
    private GenreServiceImpl genreService;
    @Mock
    private GenreDao mockedGenreDao;

    @Test
    public void getAllGenres() throws Exception {
        // Mocking expected result
        List<Genre> genreList = new ArrayList<>();
        genreList.add(GenreGenerator.getGenreForTest());
        when(mockedGenreDao.getAll()).thenReturn(genreList);
        assertEquals(1, genreService.getAll().size());

    }

    @Test
    public void enrichMoviesByGenres() throws Exception {
        List<Movie> movieList = new ArrayList<>();
        movieList.add(MovieGenerator.getMovieForTest());
        genreService.enrichMoviesWithGenres(movieList);
        assertEquals(1, movieList.size());
    }

}