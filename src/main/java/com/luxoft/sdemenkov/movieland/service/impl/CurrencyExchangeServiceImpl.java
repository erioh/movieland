package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.ExchangeRate;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CurrencyExchangeService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();


    @Autowired
    private String bankUrl;

    @Override
    public List<Movie> getMovieWithChangedCurrency(List<Movie> movieList, Currency currency) {

        ExchangeRate exchangeRate = getExchangeRateForCCY(currency);
        for (Movie movie : movieList) {
            movie.setPrice(movie.getPrice() / exchangeRate.getRate());
        }
        return movieList;
    }

    public ExchangeRate getExchangeRateForCCY(Currency currency) {
        Security.insertProviderAt(bouncyCastleProvider, 1);
        String ccy = "";
        switch (currency) {
            case USD:
                ccy = "USD";
                break;
            case EUR:
                ccy = "EUR";
                break;
        }
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");
        String currentDate = dateFormat.format(calendar.getTime());

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(bankUrl)
                .queryParam("valcode", ccy)
                .queryParam("date", currentDate)
                .queryParam("json");

        ExchangeRate[] exchangeRates = restTemplate.getForObject(builder.toUriString(), ExchangeRate[].class);
        if (exchangeRates.length == 0) {
            logger.error("No curencies were recived");
            throw new RuntimeException("No currencies were recived");
        }
        return exchangeRates[0];

    }
}
