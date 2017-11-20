package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.technical.Pair;
import com.luxoft.sdemenkov.movieland.model.technical.SortDirection;
import com.luxoft.sdemenkov.movieland.service.CurrencyValidationService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.SortDirectionValidationService;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.response.AllMoviesDto;
import com.luxoft.sdemenkov.movieland.web.response.MoviesByGenreDto;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
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
import static org.mockito.ArgumentMatchers.*;
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
    private SortService mockedSortService;
    @Mock
    private SortDirectionValidationService mockedSortDirectionValidationService;
    @Mock
    private CurrencyValidationService mockCurrencyValidationService;
    @InjectMocks
    private MovieController movieController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    public void getAllMovies() throws Exception {
        // Mocking MovieServiceImpl for MovieControllerImpl
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());

        when(mockedMovieService.getAll()).thenReturn(mockedGetAllMoviesList);
        mockMvc.perform(get("/movie/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getThreeRandomMovies() throws Exception {
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());

        when(mockedMovieService.getThreeRandomMovies()).thenReturn(mockedGetAllMoviesList);
        mockMvc.perform(get("/movie/random"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));
    }

    @Test
    public void getMoviesByGenre() throws Exception {
        List<Movie> mockedGetAllMoviesList = new ArrayList<>();
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());
        mockedGetAllMoviesList.add(MovieGenerator.getMovieForTest());

        when(mockedMovieService.getMoviesByGenre(anyInt())).thenReturn(mockedGetAllMoviesList);
        mockMvc.perform(get("/movie/genre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));
    }

    @Test
    public void getAllMoviesSortedByRating() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        Pair<SortDirection, SortDirection> pair = new Pair<>(SortDirection.DESC, SortDirection.DESC);
        when(mockedSortDirectionValidationService.getValidationErrors(anyString(), eq(null))).thenReturn(pair);

        when(mockedSortService.sortByRating(anyList(), eq(SortDirection.DESC))).thenReturn(responseGetAllMoviesList);
        mockMvc.perform(get("/movie").param("rating", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getAllMoviesSortedByPriceDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        Pair<SortDirection, SortDirection> pair = new Pair<>(SortDirection.DESC, SortDirection.DESC);
        when(mockedSortDirectionValidationService.getValidationErrors(eq(null), anyString())).thenReturn(pair);

        when(mockedSortService.sortByPrice(anyList(), eq(SortDirection.DESC))).thenReturn(responseGetAllMoviesList);
        mockMvc.perform(get("/movie").param("price", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getAllMoviesSortedByPriceAsc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        responseGetAllMoviesList.add(new AllMoviesDto(MovieGenerator.getMovieForTest()));
        Pair<SortDirection, SortDirection> pair = new Pair<>(SortDirection.ASC, SortDirection.ASC);
        when(mockedSortDirectionValidationService.getValidationErrors(eq(null), anyString())).thenReturn(pair);
        when(mockedSortService.sortByPrice(anyList(), eq(SortDirection.ASC))).thenReturn(responseGetAllMoviesList);
        mockMvc.perform(get("/movie").param("price", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8.6))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }


    @Test
    public void getMoviesByGenreSortedByRating() throws Exception {
        List<Sortable> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        Pair<SortDirection, SortDirection> pair = new Pair<>(SortDirection.DESC, SortDirection.DESC);
        when(mockedSortDirectionValidationService.getValidationErrors(anyString(), eq(null))).thenReturn(pair);
        when(mockedSortService.sortByRating(anyList(), eq(SortDirection.DESC))).thenReturn(movieDtoList);
        mockMvc.perform(get("/movie/genre/1").param("rating", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getMoviesByGenreSortedByPrice() throws Exception {
        List<Sortable> movieDtoList = new ArrayList<>();
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        movieDtoList.add(new MoviesByGenreDto(MovieGenerator.getMovieForTest()));
        Pair<SortDirection, SortDirection> pair = new Pair<>(SortDirection.DESC, SortDirection.DESC);
        when(mockedSortDirectionValidationService.getValidationErrors(eq(null), anyString())).thenReturn(pair);
        when(mockedSortService.sortByPrice(anyList(), eq(SortDirection.DESC))).thenReturn(movieDtoList);
        mockMvc.perform(get("/movie/genre/1").param("price", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").value(15))
                .andExpect(jsonPath("$[0].nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("$[0].nameNative").value("Gladiator"))
                .andExpect(jsonPath("$[0].yearOfRelease").value(2000))
                .andExpect(jsonPath("$[0].rating").value(8))
                .andExpect(jsonPath("$[0].price").value(175.0))
                .andExpect(jsonPath("$[0].picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"));

    }

    @Test
    public void getMoviesById() throws Exception {
        Movie movie = MovieGenerator.getMovieForTest();
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        when(mockedMovieService.getMovieById(anySet())).thenReturn(movieList);
        mockMvc.perform(get("/movie/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(15))
                .andExpect(jsonPath("nameRussian").value("Гладиатор"))
                .andExpect(jsonPath("nameNative").value("Gladiator"))
                .andExpect(jsonPath("yearOfRelease").value(2000))
                .andExpect(jsonPath("rating").value(8))
                .andExpect(jsonPath("price").value(175.0))
                .andExpect(jsonPath("picturePath").value("https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg"))
                .andExpect(jsonPath("description").value("Description"))
                .andExpect(jsonPath("countries[0].id").value(1))
                .andExpect(jsonPath("countries[0].name").value("Country name"))
                .andExpect(jsonPath("genres[0].id").value(1))
                .andExpect(jsonPath("genres[0].name").value("Genre name"))
                .andExpect(jsonPath("reviews[0].id").value(1))
                .andExpect(jsonPath("reviews[0].text").value("Review text"))
                .andExpect(jsonPath("reviews[0].user.id").value(1))
                .andExpect(jsonPath("reviews[0].user.nickname").value("User name"));


    }

}