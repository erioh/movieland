package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class CountryServiceTest {
    @InjectMocks
    private CountryService countryService;

    @Mock
    private CountryDao mockedCountryDao;

    @Test
    public void enrichMoviesByCountries() throws Exception {

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        when(mockedCountryDao.enrichMoviesByCountries(anyList())).thenReturn(movieList);
        assertEquals(1, countryService.ecrichMoviesByCountries(movieList).size());
    }

}