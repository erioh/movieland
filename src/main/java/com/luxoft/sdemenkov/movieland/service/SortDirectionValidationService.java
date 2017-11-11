package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Pair;
import com.luxoft.sdemenkov.movieland.model.SortDirection;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

public interface SortDirectionValidationService {
    Pair<SortDirection, SortDirection> getValidationErrors(String ratingDirection, String priceDirection);
}
