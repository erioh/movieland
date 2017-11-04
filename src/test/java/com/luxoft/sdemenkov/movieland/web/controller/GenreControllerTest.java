package com.luxoft.sdemenkov.movieland.web.controller;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.MoviesByGenreDTO;
import com.luxoft.sdemenkov.testutils.GenreGenerator;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import com.luxoft.sdemenkov.movieland.web.controller.rest.GenreController;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@WebAppConfiguration
public class GenreControllerTest {
    private MockMvc mockMvc;
    @Mock
    private GenreService mockedGenreService;
    @Mock
    private SortService mockedSortService;
    @Mock
    private MovieService mockedMovieService;

    @InjectMocks
    private GenreController genreController;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(genreController).build();
    }


    @Test
    public void getAllGenresTest() throws Exception {
        List<Genre> mockedGenreList = new ArrayList<>();
        mockedGenreList.add(GenreGenerator.getGenreForTest());
        mockedGenreList.add(GenreGenerator.getGenreForTest());
        when(mockedGenreService.getAllGenres()).thenReturn(mockedGenreList);
        mockMvc.perform(get("/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Name"))
        ;

    }





}