package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Pair;
import com.luxoft.sdemenkov.movieland.model.SortDirection;

public interface SortDirectionValidationService {
    Pair<SortDirection, SortDirection> getValidationErrors(String ratingDirection, String priceDirection);
}
