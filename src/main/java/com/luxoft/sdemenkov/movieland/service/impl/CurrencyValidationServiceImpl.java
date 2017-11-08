package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.service.CurrencyValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
class CurrencyValidationServiceImpl implements CurrencyValidationService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public ResponseEntity<String> getValidationErrors(String currency) {
        if(currency != null && Currency.getCurrency(currency) == null) {
            log.error("Invalid currency code is used. Used code is:{}", currency);
            return new ResponseEntity<String>("Invalid currency code is used. Used code is: " + currency, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
