package com.luxoft.sdemenkov.movielend.web.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.CountryDaoImpl;
import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by dp-ptcstd-43 on 10/31/2017.
 */
public class CountryServiceTest {
    private ApplicationContext context;
    private CountryService countryService;
    private CountryDao mockedCountryDao;

    @Before
    public void setUp() throws Exception {
        context = new FileSystemXmlApplicationContext("./src/test/resources/spring-test-config.xml");
        countryService = (CountryService) context.getBean("countryService");
        mockedCountryDao = mock(CountryDaoImpl.class);
    }

    @Test
    public void ecrichMoviesByCountries() throws Exception {

        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie());
        when(mockedCountryDao.enrichMoviesByCountries(anyList())).thenReturn(movieList);
        countryService.setCountryDao(mockedCountryDao);
        assertEquals(1, countryService.ecrichMoviesByCountries(movieList).size());
    }

}