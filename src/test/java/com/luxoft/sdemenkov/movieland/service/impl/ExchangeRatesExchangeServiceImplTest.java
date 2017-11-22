package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.business.Currency;
import com.luxoft.sdemenkov.movieland.model.business.ExchangeRate;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRatesExchangeServiceImplTest {
    @InjectMocks
    private CurrencyExchangeServiceImpl currencyExchangeService;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void getMovieWithChangedCurrency() throws Exception {
        ReflectionTestUtils.setField(currencyExchangeService, "bankUrl", "http://mocked.url.com");
        List<Movie> movieListExpected = new ArrayList<>();
        List<Movie> movieListActual = new ArrayList<>();
        movieListExpected.add(MovieGenerator.getMovieForTest());
        movieListActual.add(MovieGenerator.getMovieForTest());
        ExchangeRate exchangeRate = new ExchangeRate();
        exchangeRate.setRate(25);
        ExchangeRate[] exchangeRates = {exchangeRate};
        when(restTemplate.getForObject(any(), any())).thenReturn(exchangeRates);

        movieListActual = currencyExchangeService.getMovieWithChangedCurrency(movieListActual, Currency.EUR);
        assertTrue(movieListActual.get(0).getPrice().compareTo(movieListExpected.get(0).getPrice()) < 0);


    }


}