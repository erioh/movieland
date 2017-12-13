package com.luxoft.sdemenkov.movieland.service;


import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import org.springframework.transaction.annotation.Transactional;

public interface RateService {
    void saveRate(Rate rate);

    void flushAll();

    void enrichWithNotSavedRates(Movie movie);
}
