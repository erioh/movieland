package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.technical.Pair;
import com.luxoft.sdemenkov.movieland.model.technical.SortDirection;

public interface SortDirectionValidationService {
    Pair<SortDirection, SortDirection> getValidationErrors(String ratingDirection, String priceDirection);
}
