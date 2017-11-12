package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.ReviewDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.ReviewToMovieRowMapper;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sergeydemenkov on 04.11.17.
 */
@Repository
public class JdbcReviewDao implements ReviewDao {
    private final static ReviewToMovieRowMapper REVIEW_TO_MOVIE_ROW_MAPPER = new ReviewToMovieRowMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getReviewByMovieIdsSQL;

    @Override
    public void enrichMoviesWithReviews(List<Movie> movieList) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        List<Integer> ids = new ArrayList<>();
        for (Movie movie : movieList) {
            ids.add(movie.getId());
        }
        mapSqlParameterSource.addValue("ids", ids);
        List<Pair<Integer, Review>> responceList = namedParameterJdbcTemplate.query(getReviewByMovieIdsSQL, mapSqlParameterSource, REVIEW_TO_MOVIE_ROW_MAPPER);
        for (Movie movie : movieList) {
            List<Review> reviewList = new ArrayList<>();
            for (Pair<Integer, Review> integerReviewPair : responceList) {
                if (movie.getId() == integerReviewPair.getKey()) {
                    reviewList.add(integerReviewPair.getValue());
                }
            }
            movie.setReviewList(reviewList);
        }
    }
}
