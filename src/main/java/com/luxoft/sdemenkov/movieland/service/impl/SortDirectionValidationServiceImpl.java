package com.luxoft.sdemenkov.movieland.service.impl;

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
    public ResponseEntity<String> getValidationErrors(String ratingDirection, String priceDirection) {
        log.debug("Validation of sort direction request is stated");
        if (ratingDirection != null && priceDirection != null) {
            log.error("You can't sort response by rating and price at the same time. Parameter rating = {} and parameter price = {}", ratingDirection, priceDirection);
            return new ResponseEntity<>("You can't sort response by rating and price at the same time", HttpStatus.BAD_REQUEST);
        }
        if (ratingDirection != null && SortDirection.getDirection(ratingDirection) != SortDirection.DESC) {
            log.error("Invalid value of parameter 'rating' is {}", ratingDirection);
            return new ResponseEntity<>("Invalid value of parameter 'rating' is '" + ratingDirection + "'", HttpStatus.BAD_REQUEST);
        }
        if (priceDirection != null && SortDirection.getDirection(priceDirection) == null) {
            log.error("Invalid Sort Direction is used. Used code is: " + priceDirection);
            return new ResponseEntity<>("Invalid Sort Direction is used. Used code is: " + priceDirection, HttpStatus.BAD_REQUEST);
        }
        log.debug("Validation of sort direction request is finished");
        return null;
    }
}
