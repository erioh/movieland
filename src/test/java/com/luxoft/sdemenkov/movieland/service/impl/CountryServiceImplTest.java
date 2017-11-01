package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.impl.CountryServiceImpl;
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
        movieList.add(new Movie());
        when(mockedCountryDao.enrichMoviesByCountries(anyList())).thenReturn(movieList);
        assertEquals(1, countryService.ecrichMoviesByCountries(movieList).size());
    }

}