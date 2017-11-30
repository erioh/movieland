package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.dao.api.RateDao;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCounPair;
import com.luxoft.sdemenkov.movieland.service.RateService;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import com.luxoft.sdemenkov.movieland.web.exception.WrongRateValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RateServiceImpl implements RateService {
    @Autowired
    RateDao rateDao;

    @Override
    @Transactional
    public void saveRate(Rate rate) {
        validateRate(rate);
        int userId = rate.getUserId();
        int movieId = rate.getMovieId();
        rateDao.deleteUsersRateForMovie(userId, movieId);
        rateDao.saveRate(rate);
        RatingToCounPair pair = rateDao.getRatingToCountPair(movieId);
        rateDao.recalculateRateForMovie(movieId, pair);
    }

    public void validateRate(Rate rate) {
        double rating = rate.getRating();
        if (rating < 1 || rating > 10) {
            throw new WrongRateValueException(rating);
        }
    }
}
