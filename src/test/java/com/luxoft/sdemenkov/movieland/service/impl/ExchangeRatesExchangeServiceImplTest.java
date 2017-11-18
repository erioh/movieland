package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class ExchangeRatesExchangeServiceImplTest {
    @Autowired
    private CurrencyExchangeServiceImpl currencyExchangeService;

    @Test
    public void getMovieWithChangedCurrency() throws Exception {
        List<Movie> movieListExpected = new ArrayList<>();
        List<Movie> movieListActual = new ArrayList<>();
        movieListExpected.add(MovieGenerator.getMovieForTest());
        movieListActual.add(MovieGenerator.getMovieForTest());

        try {
            movieListActual = currencyExchangeService.getMovieWithChangedCurrency(movieListActual, Currency.EUR);
            assertTrue(movieListActual.get(0).getPrice().compareTo(movieListExpected.get(0).getPrice()) < 0);
        } catch (HttpServerErrorException e) {
                    }


    }

}