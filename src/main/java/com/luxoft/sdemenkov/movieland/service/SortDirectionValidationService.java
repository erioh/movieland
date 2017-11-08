package com.luxoft.sdemenkov.movieland.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

public interface SortDirectionValidationService {
    ResponseEntity<String> getValidationErrors(String ratingDirection, String priceDirection);
}
