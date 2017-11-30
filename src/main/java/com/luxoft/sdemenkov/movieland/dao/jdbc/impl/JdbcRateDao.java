package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.RateDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.RatingToCountOfRatedUsersRowMapper;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCounPair;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class JdbcRateDao implements RateDao {
    private final static RatingToCountOfRatedUsersRowMapper RATING_TO_COUNT_OF_RATED_USERS_ROW_MAPPER = new RatingToCountOfRatedUsersRowMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String saveRateSql;
    @Autowired
    private String deleteUsersRateForMovieSql;
    @Autowired
    private String getSumRatingWithCountOfRatedUsersSql;
    @Autowired
    private String updateRateForMovieSql;
    @Autowired
    private String isRatingAddedSql;

    @Override
    public int saveRate(Rate rate) {
        log.debug("saveRate. saving rate = {}", rate);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", rate.getMovieId());
        mapSqlParameterSource.addValue("userId", rate.getUserId());
        mapSqlParameterSource.addValue("rating", rate.getRating());
        return namedParameterJdbcTemplate.update(saveRateSql, mapSqlParameterSource);
    }

    @Override
    public int deleteUsersRateForMovie(int userId, int movieId) {
        log.debug("deleteUsersRateForMovie. removing rated from user with userId = {} for movie with movieId = {}", userId, movieId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movieId);
        mapSqlParameterSource.addValue("userId", userId);
        return namedParameterJdbcTemplate.update(deleteUsersRateForMovieSql, mapSqlParameterSource);
    }

    @Override
    public RatingToCounPair getRatingToCountPair(int movieId) {
        log.debug("getRatingToCountPair. getting SUM(rating) and count of rated users for movie with id = {}", movieId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movieId);
        RatingToCounPair pair = namedParameterJdbcTemplate.queryForObject(getSumRatingWithCountOfRatedUsersSql, mapSqlParameterSource, RATING_TO_COUNT_OF_RATED_USERS_ROW_MAPPER);
        log.debug("getRatingToCountPair. pair = {}", pair);
        return pair;
    }

    @Override
    public boolean isRatingAdded(int movieId, int userId) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movieId);
        mapSqlParameterSource.addValue("userId", userId);
        int count = namedParameterJdbcTemplate.queryForObject(isRatingAddedSql, mapSqlParameterSource, Integer.class);
        return (count > 0) ? true : false;
    }

    @Override
    public double recalculateRateForMovie(int movieId, RatingToCounPair pair) {
        log.debug("recalculateRateForMovie. recalculating rating for movie with id = {} with pair = {}", movieId, pair);
        BigDecimal rating = BigDecimal.valueOf(pair.getRatingSum());
        BigDecimal count = BigDecimal.valueOf(pair.getCount());
        BigDecimal newRating = rating.divide(count, BigDecimal.ROUND_HALF_DOWN).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        double result = newRating.doubleValue();
        log.debug("recalculateRateForMovie. result of caclulation is {} for pair {}", result, pair);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("newRating", result);
        mapSqlParameterSource.addValue("movieId", movieId);
        int countOfChangedRows = namedParameterJdbcTemplate.update(updateRateForMovieSql, mapSqlParameterSource);
        if (countOfChangedRows == 0 ) {
            throw new WrongMovieIdException(movieId);
        }
        return result;
    }
}
