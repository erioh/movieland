package com.luxoft.sdemenkov.movielend.web.controller;

import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import com.luxoft.sdemenkov.movielend.web.responce.ResponceGetAllGenres;
import com.luxoft.sdemenkov.movielend.web.responce.ResponceGetAllMovies;
import com.luxoft.sdemenkov.movielend.web.responce.ResponceGetThreeRandomMovies;
import com.luxoft.sdemenkov.movielend.web.service.GenreService;
import com.luxoft.sdemenkov.movielend.web.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieControllerTest {
    private ApplicationContext context;
    private MovieController movieController;
    private MovieService mockedMovieService;
    private GenreService mockedGenreService;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/main/webapp/WEB-INF/spring/spring-test-config.xml");
        movieController = (MovieController) context.getBean("movieController");
        mockedMovieService = mock(MovieService.class);
        mockedGenreService = mock(GenreService.class);

    }

    @Test
    public void getAllMovies() throws Exception {

        // Creating expected result
        ResponceGetAllMovies expectedGetAllMoviesresponce = new ResponceGetAllMovies();
        expectedGetAllMoviesresponce.setId(15);
        expectedGetAllMoviesresponce.setNameRussian("Gladiator");
        expectedGetAllMoviesresponce.setNameNative("Gladiator");
        expectedGetAllMoviesresponce.setYearOfRelease(2000);
        expectedGetAllMoviesresponce.setRating(8.6);
        expectedGetAllMoviesresponce.setPrice(175.0);
        expectedGetAllMoviesresponce.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");

        // Mocking MovieService for MovieController
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(getMovieForTest());

        when(mockedMovieService.getAllMovies()).thenReturn(mockedGetAllMoviesList);
        movieController.setMovieService(mockedMovieService);

        //Test
        List<ResponceGetAllMovies> responceGetAllMoviesList = movieController.getAllMovies();
        ResponceGetAllMovies actualResponce = responceGetAllMoviesList.get(0);

        assertEquals(expectedGetAllMoviesresponce.getId(), actualResponce.getId());
        assertEquals(expectedGetAllMoviesresponce.getNameRussian(), actualResponce.getNameRussian());
        assertEquals(expectedGetAllMoviesresponce.getNameNative(), actualResponce.getNameNative());
        assertEquals(expectedGetAllMoviesresponce.getYearOfRelease(), actualResponce.getYearOfRelease());
        assertEquals(expectedGetAllMoviesresponce.getRating(), actualResponce.getRating(), 0);
        assertEquals(expectedGetAllMoviesresponce.getPrice(), actualResponce.getPrice(), 0);
        assertEquals(expectedGetAllMoviesresponce.getPicturePath(), actualResponce.getPicturePath());
    }

    @Test
    public void getThreeRandomMovies() throws Exception {

        // Mocking data and movieService for test
        List<Movie> movieList = new ArrayList<>(Arrays.asList(new Movie[]{
                getMovieForTest(),
                getMovieForTest(),
                getMovieForTest()}));
        when(mockedMovieService.getThreeRundomMovies()).thenReturn(movieList);
        movieController.setMovieService(mockedMovieService);

        List<ResponceGetThreeRandomMovies> responceGetThreeRandomMovies = movieController.getThreeRandomMovies();
        assertEquals(3, responceGetThreeRandomMovies.size());
        assertNotEquals(0, responceGetThreeRandomMovies.get(0).getCountryList().size());
        assertNotEquals(0, responceGetThreeRandomMovies.get(0).getGenreList().size());

    }


    @Test
    public void getAllGenresTest() throws Exception {

        // Mocking GenreService for test
        List<Genre> genreList = new ArrayList<>(Arrays.asList(new Genre[]{
                getGenreForTest(),
                getGenreForTest(),
                getGenreForTest()
        }));
        when(mockedGenreService.getAllGenres()).thenReturn(genreList);
        movieController.setGenreService(mockedGenreService);

        // Test
        List<ResponceGetAllGenres> responceGetAllGenresList = movieController.getAllGenres();
        assertEquals(3, responceGetAllGenresList.size());
        assertEquals(1, responceGetAllGenresList.get(0).getId());
        assertEquals("Name", responceGetAllGenresList.get(0).getName());

    }

    private Movie getMovieForTest() {
        Movie movie = new Movie();
        movie.setId(15);
        movie.setNameRussian("Gladiator");
        movie.setNameNative("Gladiator");
        movie.setYearOfRelease(2000);
        movie.setRating(8.6);
        movie.setPrice(175.0);
        movie.setPicturePath("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg");
        List<Genre> genreList = new ArrayList<>();
        genreList.add(new Genre());
        movie.setGenreList(genreList);
        List<Country> countryList = new ArrayList<>();
        countryList.add(new Country());
        movie.setCountryList(countryList);
        return movie;
    }

    private Genre getGenreForTest() {
        Genre genre = new Genre();
        genre.setId(1);
        genre.setName("Name");
        return genre;
    }

}