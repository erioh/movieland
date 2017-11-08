package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.service.CurrencyValidationService;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

/**
 * Created by sergeydemenkov on 08.11.17.
 */
public class CurrencyValidationServiceImplTest {
    CurrencyValidationService currencyValidationService = new CurrencyValidationServiceImpl();
    @Test
    public void getValidationErrors() throws Exception {

        ResponseEntity<String> validationErrors = currencyValidationService.getValidationErrors("USD");
        assertNull(validationErrors);
        validationErrors = currencyValidationService.getValidationErrors("EUR");
        assertNull(validationErrors);
        validationErrors = currencyValidationService.getValidationErrors("SSS");
        assertNotNull(validationErrors);
    }

}