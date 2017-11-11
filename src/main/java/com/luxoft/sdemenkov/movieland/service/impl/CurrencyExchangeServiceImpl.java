package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.ExchangeRate;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CurrencyExchangeService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.security.Security;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class CurrencyExchangeServiceImpl implements CurrencyExchangeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
    private final DateFormat dateFormat = new SimpleDateFormat("YYYYMMdd");



    @Value("${bank.url}")
    private String bankUrl;

    @Override
    public List<Movie> getMovieWithChangedCurrency(List<Movie> movieList, Currency currency) {

        ExchangeRate exchangeRate = getExchangeRateForCurrency(currency);
        for (Movie movie : movieList) {
            BigDecimal bigDecimalExchangeRate = new BigDecimal(exchangeRate.getRate());
            movie.setPrice(movie.getPrice().divide(bigDecimalExchangeRate, BigDecimal.ROUND_HALF_DOWN));
        }
        return movieList;
    }

    public ExchangeRate getExchangeRateForCurrency(Currency currency) {
        Security.insertProviderAt(bouncyCastleProvider, 1);
        String currentDate = dateFormat.format(new Date().getTime());

        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(bankUrl)
                .queryParam("valcode", currency)
                .queryParam("date", currentDate)
                .queryParam("json");

        ExchangeRate[] exchangeRates = restTemplate.getForObject(builder.toUriString(), ExchangeRate[].class);
        if (exchangeRates.length == 0) {
            logger.error("No currencies were received");
            throw new RuntimeException("No currencies were received");
        }
        return exchangeRates[0];

    }
}
