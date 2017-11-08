package com.luxoft.sdemenkov.movieland.service;

import org.springframework.http.ResponseEntity;

/**
 * Created by sergeydemenkov on 08.11.17.
 */
public interface CurrencyValidationService {
    ResponseEntity<String> getValidationErrors(String currency);
}
