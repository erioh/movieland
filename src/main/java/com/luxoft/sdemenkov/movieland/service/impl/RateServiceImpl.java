package com.luxoft.sdemenkov.movieland.service.impl;


import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class RateServiceImpl implements RateService {
    Logger logger = LoggerFactory.getLogger(getClass());


    private Queue<Rate> rateQueue = new ConcurrentLinkedQueue<>();
    @Autowired
    private MovieDao movieDao;

    @Override
    public void saveRate(Rate rate) {
        logger.debug("saveRate. saving rate = {}", rate);
        for (Rate storredRate : rateQueue) {
            if (storredRate.getMovieId() == rate.getMovieId() && storredRate.getUserId() == rate.getUserId()) {
                rateQueue.remove(storredRate);
            }
        }
        rateQueue.add(rate);

    }
    
    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${cron.dao.rates.buffered.flush}", initialDelayString = "${cron.dao.rates.buffered.flush}")
    public void flushAll() {
        logger.debug("Flushing rates. List if rates = {}", rateQueue);
            movieDao.rateMovies(rateQueue);
        rateQueue.clear();
    }

    @Override
    public void enrichWithNotSavedRates(Movie movie) {
        logger.info("Starting enrich with actual rating");

        logger.debug("Getting sum of stored filtered rates and count of users");
        int count = movie.getNumberOfRates();
        double rating = movie.getRating() * count;

        logger.debug("Adding not stored ratings {}", rateQueue);
        for (Rate rate : rateQueue) {
            if (rate.getMovieId() == movie.getId()) {
                count++;
                rating += rate.getRating();
            }
        }

        logger.debug("Calculating ratings AVG");
        BigDecimal sumOfRatings = BigDecimal.valueOf(rating);
        logger.debug("getting sumOfRatings {}", sumOfRatings);
        BigDecimal countOfRatings = BigDecimal.valueOf(count);
        logger.debug("getting countOfRatings {}", countOfRatings);

        BigDecimal calculatedRating;
        if(countOfRatings.equals(BigDecimal.ZERO)) {
            logger.debug("Movie {} is not rated yet", movie);
            calculatedRating = BigDecimal.ZERO;
        } else  {
            calculatedRating = sumOfRatings
                    .divide(countOfRatings)
                    .setScale(2, BigDecimal.ROUND_DOWN);
            logger.debug("getting calculatedRating {}", calculatedRating);
        }
        movie.setRating(calculatedRating.doubleValue());
    }
}
