package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.service.CurrencyValidationService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CurrencyValidationServiceImplTest {
    private CurrencyValidationService currencyValidationService = new CurrencyValidationServiceImpl();

    @Test
    public void getValidationErrors() throws Exception {

        Currency currency = currencyValidationService.getCurrency("USD");
        assertEquals(Currency.USD, currency);
        currency = currencyValidationService.getCurrency("EUR");
        assertEquals(Currency.EUR, currency);
        String errorMessage = "";

        try {
            currency = currencyValidationService.getCurrency("SSS");
        } catch (RuntimeException e) {
            errorMessage = e.getMessage();
        }
        assertEquals("Invalid currency code is used. Used code is: SSS", errorMessage);
    }

}