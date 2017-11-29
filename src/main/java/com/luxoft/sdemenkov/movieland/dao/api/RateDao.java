package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCounPair;

public interface RateDao {
    int saveRate(Rate rate);

    int deleteUsersRateForMovie(int userId, int movieId);

    double recalculateRateForMovie(int movieId, RatingToCounPair pair);

    RatingToCounPair getRatingToCountPair(int movieId);

    boolean isRatingAdded(int movieId, int userId);
}
