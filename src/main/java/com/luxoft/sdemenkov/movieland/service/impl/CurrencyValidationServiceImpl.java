package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.service.CurrencyValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
class CurrencyValidationServiceImpl implements CurrencyValidationService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public Currency getCurrency(String currency) {
        if (currency != null && Currency.getCurrency(currency) == null) {
            log.error("Invalid currency code is used. Used code is:{}", currency);
            throw new RuntimeException("Invalid currency code is used. Used code is: " + currency);
        }
        return Currency.getCurrency(currency);
    }
}
