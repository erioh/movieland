package com.luxoft.sdemenkov.movieland.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Currency {
    USD("USD"), EUR("EUR");
    private static final Logger logger = LoggerFactory.getLogger(Currency.class);
    private final String code;

    Currency(String code) {
        this.code = code;
    }

    public static Currency getCurrency(String code) {
        for (Currency currency : values()) {
            if (currency.code.equalsIgnoreCase(code)) {
                return currency;
            }
        }
        return null;
    }
}
