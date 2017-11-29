package com.luxoft.sdemenkov.movieland.dao.cache;

import com.luxoft.sdemenkov.movieland.dao.api.RateDao;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCounPair;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CachedRateDao implements RateDao {
    private Map<Integer, Rate> movieIdToRateMap = new ConcurrentHashMap<>();

    @Override
    public int saveRate(Rate rate) {
        if (movieIdToRateMap.containsValue(rate)) {
            return 1;
        }
        movieIdToRateMap.put(rate.getMovieId(), rate);
        return 1;
    }

    @Override
    public int deleteUsersRateForMovie(int userId, int movieId) {
        return 1;
    }

    @Override
    public double recalculateRateForMovie(int movieId, RatingToCounPair pair) {
        return 0;
    }

    @Override
    public RatingToCounPair getRatingToCountPair(int movieId) {
        return null;
    }

    @Override
    public boolean isRatingAdded(int movieId, int userId) {
        return false;
    }
}
