package com.luxoft.sdemenkov.movielend.web.controller;

import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import com.luxoft.sdemenkov.movielend.web.service.GenreService;
import com.luxoft.sdemenkov.movielend.web.service.MovieService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class MovieControllerTest {
    private MockMvc mockMvc;
    @Mock
    private MovieService mockedMovieService;
    @Mock
    private GenreService mockedGenreService;
    @InjectMocks
    private MovieController movieController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void getAllMovies() throws Exception {
        // Mocking MovieService for MovieController
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(getMovieForTest());

        when(mockedMovieService.getAllMovies()).thenReturn(mockedGetAllMoviesList);
        mockMvc.perform(get("/v1/movie"))
//                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Gladiator"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getThreeRandomMovies() throws Exception {
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(getMovieForTest());
        mockedGetAllMoviesList.add(getMovieForTest());
        mockedGetAllMoviesList.add(getMovieForTest());

        when(mockedMovieService.getThreeRandomMovies()).thenReturn(mockedGetAllMoviesList);
        mockMvc.perform(get("/v1/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Gladiator"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));
    }


    @Test
    public void getAllGenresTest() throws Exception {
        List<Genre> mockedGenreList = new ArrayList<>();
        mockedGenreList.add(getGenreForTest());
        mockedGenreList.add(getGenreForTest());
        when(mockedGenreService.getAllGenres()).thenReturn(mockedGenreList);
        mockMvc.perform(get("/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Name"))
        ;

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