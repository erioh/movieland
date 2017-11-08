package com.luxoft.sdemenkov.movieland.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.InvalidParameterException;

public enum Currency {
    USD("USD"), EUR("EUR");
    private final String code;
    private static final Logger logger = LoggerFactory.getLogger(Currency.class);

    Currency(String code) {
        this.code = code;
    }

    public static Currency getCurrency(String code) {
        for (Currency currency : values()) {
            if(currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return null;
    }
}
