package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by dp-ptcstd-43 on 10/31/2017.
 */
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class CountryServiceImplTest {
    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryDao mockedCountryDao;

    @Test
    public void enrichMoviesByCountries() throws Exception {

        List<Movie> movieList = new ArrayList<>();
        movieList.add(MovieGenerator.getMovieForTest());
//        when(mockedCountryDao.enrichMoviesWithCountries(anyList())).;
        countryService.ecrichMoviesWithCountries(movieList);
        assertEquals(1, movieList.size());
    }

}