package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Pair;
import com.luxoft.sdemenkov.movieland.model.SortDirection;
import com.luxoft.sdemenkov.movieland.service.SortDirectionValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SortDirectionValidationServiceImpl implements SortDirectionValidationService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Override
    public Pair<SortDirection, SortDirection> getValidationErrors(String ratingDirection, String priceDirection) {
        log.debug("Validation of sort direction request is stated");
        if (ratingDirection != null && priceDirection != null) {
            log.error("You can't sort response by rating and price at the same time. Parameter rating = {} and parameter price = {}", ratingDirection, priceDirection);
            throw new RuntimeException("You can't sort response by rating and price at the same time");
        }
        if (ratingDirection != null && SortDirection.getDirection(ratingDirection) != SortDirection.DESC) {
            log.error("Invalid value of parameter 'rating' is {}", ratingDirection);
            throw new RuntimeException("Invalid value of parameter 'rating' is '" + ratingDirection + "'");
        }
        if (priceDirection != null && SortDirection.getDirection(priceDirection) == null) {
            log.error("Invalid Sort Direction is used. Used code is: " + priceDirection);
            throw new RuntimeException("Invalid Sort Direction is used. Used code is: " + priceDirection);
        }
        log.debug("Validation of sort direction request is finished");
        return new Pair<SortDirection, SortDirection>(SortDirection.getDirection(ratingDirection), SortDirection.getDirection(priceDirection));
    }
}
