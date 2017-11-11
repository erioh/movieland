package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Currency;
import org.springframework.http.ResponseEntity;

/**
 * Created by sergeydemenkov on 08.11.17.
 */
public interface CurrencyValidationService {
    Currency getCurrency(String currency);
}
