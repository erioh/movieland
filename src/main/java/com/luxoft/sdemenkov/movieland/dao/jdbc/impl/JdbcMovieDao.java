package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.dao.jdbc.impl.util.QueryBuilder;
import com.luxoft.sdemenkov.movieland.dao.mapper.MovieRowMapper;
import com.luxoft.sdemenkov.movieland.dao.mapper.RatingToCountOfRatedUsersRowMapper;
import com.luxoft.sdemenkov.movieland.dao.mapper.UserToRateRowMapper;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCountPair;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final static MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final static Random RANDOM_GENERATOR = new Random();
    private final static RatingToCountOfRatedUsersRowMapper RATING_TO_COUNT_OF_RATED_USERS_ROW_MAPPER = new RatingToCountOfRatedUsersRowMapper();
    private static final UserToRateRowMapper USER_TO_RATE_ROW_MAPPER = new UserToRateRowMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getMoviesByGenreIdSQL;
    @Autowired
    private String getAllMoviesSQL;
    @Autowired
    private String getCountOfMoviesSQL;
    @Autowired
    private String getMovieByIdSQL;
    @Autowired
    private String saveMovieSQL;
    @Autowired
    private String updateMovieSQL;
    @Autowired
    private String searchByTitleSQL;
    @Autowired
    private String inAccurateUpdateRateForMovieSql;

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

    private Queue<Rate> rateQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void removeRate(Rate rate) {
        rateQueue.remove(rate);
    }

    @Override
    public List<Movie> getAll() {
        List<Movie> movieList = jdbcTemplate.query(getAllMoviesSQL, MOVIE_ROW_MAPPER);
        log.debug("Calling method getAll. with query = {}", getAllMoviesSQL);
        log.debug("Calling method getAll. Result = {}", movieList);
        return movieList;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        Set<Integer> ids = new HashSet<>();
        int countOfRows = getCountOfMovies();
        int searchedCount = countOfRows >= 3 ? 3 : countOfRows;
        while (ids.size() != searchedCount) {
            ids.add(RANDOM_GENERATOR.nextInt(countOfRows + 1));
        }
        List<Movie> movieList = getMovieListByIds(ids);
        while (movieList.size() < 3) {
            movieList.add(movieList.get(0));
        }
        return movieList;
    }

    @Override
    public int getCountOfMovies() {
        int countOfMovies = jdbcTemplate.queryForObject(getCountOfMoviesSQL, Integer.class);
        log.debug("Count of movies = {}", countOfMovies);
        return countOfMovies;
    }

    @Override
    public List<Movie> getMovieListByIds(Set<Integer> ids) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("ids", ids);
        List<Movie> movie = namedParameterJdbcTemplate.query(getMovieByIdSQL, sqlParameterSource, MOVIE_ROW_MAPPER);
        log.debug("Movie with id = {} was selected. Movie = {}", ids, movie.get(0));
        return movie;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> movieList = jdbcTemplate.query(getMoviesByGenreIdSQL, new Object[]{genreId}, MOVIE_ROW_MAPPER);
        log.debug("Method getMoviesByGenre is called with sql = {} and genre_id = {} ", getMoviesByGenreIdSQL, genreId);
        log.debug("Method getMoviesByGenre is called. Result is {}", movieList);
        return movieList;
    }

    @Override
    public void save(Movie movie) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        log.debug("Starting to save movie into DB");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nameRussian", movie.getNameRussian());
        mapSqlParameterSource.addValue("nameNative", movie.getNameNative());
        mapSqlParameterSource.addValue("yearOfRelease", movie.getYearOfRelease());
        mapSqlParameterSource.addValue("description", movie.getDescription());
        mapSqlParameterSource.addValue("rating", movie.getRating());
        mapSqlParameterSource.addValue("price", movie.getPrice());
        mapSqlParameterSource.addValue("picturePath", movie.getPicturePath());
        namedParameterJdbcTemplate.update(saveMovieSQL, mapSqlParameterSource, keyHolder);
        Number key = keyHolder.getKey();
        int id = key.intValue();
        log.debug("Movie {} is saved with id = {}", movie, id);
        movie.setId(key.intValue());
    }

    @Override
    public void update(Movie movie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nameRussian", movie.getNameRussian());
        mapSqlParameterSource.addValue("nameNative", movie.getNameNative());
        mapSqlParameterSource.addValue("yearOfRelease", movie.getYearOfRelease());
        mapSqlParameterSource.addValue("description", movie.getDescription());
        mapSqlParameterSource.addValue("rating", movie.getRating());
        mapSqlParameterSource.addValue("price", movie.getPrice());
        mapSqlParameterSource.addValue("picturePath", movie.getPicturePath());
        mapSqlParameterSource.addValue("movieId", movie.getId());
        namedParameterJdbcTemplate.update(updateMovieSQL, mapSqlParameterSource);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nameRussian", title);
        mapSqlParameterSource.addValue("nameNative", title);
        List<Movie> movieList = namedParameterJdbcTemplate.query(searchByTitleSQL, mapSqlParameterSource, MOVIE_ROW_MAPPER);
        log.debug("searchByTitle. movies {} were received with title like {}", movieList, title);
        return movieList;
    }

    @Override
    public List<Movie> searchByTitle(String title, int pageNumber, int moviesPerPage) {
        String searchByTitleSQLLimited = QueryBuilder.forQuery(searchByTitleSQL)
                .withLimit(pageNumber, moviesPerPage)
                .build();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("nameRussian", title);
        mapSqlParameterSource.addValue("nameNative", title);
        List<Movie> movieList = namedParameterJdbcTemplate.query(searchByTitleSQLLimited, mapSqlParameterSource, MOVIE_ROW_MAPPER);
        log.debug("searchByTitle. movies {} were received with title like {}", movieList, title);
        return movieList;
    }

    @Override
    public double recalculateRateForMovie(int movieId, RatingToCountPair pair) {
        log.debug("recalculateRateForMovieInacurate. recalculating rating for movie with id = {} with pair = {}", movieId, pair);

        BigDecimal rating = BigDecimal.valueOf(pair.getRatingSum());
        BigDecimal count = BigDecimal.valueOf(pair.getCount());
        BigDecimal newRating = rating.divide(count).setScale(2, BigDecimal.ROUND_DOWN);

        double result = newRating.doubleValue();

        log.debug("recalculateRateForMovie. result of calculation is {} for pair {}", result, pair);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("newRating", result);
        mapSqlParameterSource.addValue("movieId", movieId);

        int countOfChangedRows = namedParameterJdbcTemplate.update(inAccurateUpdateRateForMovieSql, mapSqlParameterSource);
        if (countOfChangedRows == 0) {
            log.warn("countOfChangedRows is {}", countOfChangedRows);
            throw new WrongMovieIdException(movieId);
        }

        return result;
    }

    @Override
    public Queue<Rate> getBufferedRates() {
        return rateQueue;
    }

    @Override
    @Transactional
    public int flushAll() {
        log.debug("Flushing rates. List if rates = {}", rateQueue);
        int count = 0;
        for (Rate rate : rateQueue) {
            try {
                flush(rate);
            } catch (WrongMovieIdException e) {
                log.warn("Movie with id = {} is not present in DB. Rating is not added", rate.getMovieId());
                log.warn(e.getMessage());
            } finally {
                rateQueue.remove(rate);
            }
            count++;
        }
        return count;
    }

    @Override
    @Transactional
    public double flush(Rate rate) {
        deleteUsersRateForMovie(rate.getUserId(), rate.getMovieId());
        saveRateToDb(rate);
        RatingToCountPair pair = getRatingToCountPair(rate.getMovieId());
        return recalculateRateForMovie(rate.getMovieId(), pair);
    }

    @Override
    public void saveRateToDb(Rate rate) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", rate.getMovieId());
        mapSqlParameterSource.addValue("userId", rate.getUserId());
        mapSqlParameterSource.addValue("rating", rate.getRating());
        namedParameterJdbcTemplate.update(saveRateSql, mapSqlParameterSource);
    }

    @Override
    public Movie enrichMovieWithActualRates(Movie movie) {
        log.info("Starting enrich with actual rating");

        Movie localMovie = new Movie(movie);
        log.debug("Getting sum of stored filtered rates and count of users");
        int count = localMovie.getNumberOfRates();
        double rating = localMovie.getRating() * count;

        log.debug("Adding not stored ratings {}", rateQueue);
        for (Rate rate : rateQueue) {
            try {
                if (rate.getMovieId() == localMovie.getId()) {
                    count++;
                    double rateRating = rate.getRating();
                }
            } catch (NullPointerException e) {
                log.warn("enrichMovieWithActualRates. Rate for movie with id {} is not available any more in buffer", localMovie.getId());
            }
        }

        log.debug("Calculating ratings AVG");
        BigDecimal sumOfRatings = BigDecimal.valueOf(rating);
        log.debug("getting sumOfRatings {}", sumOfRatings);
        BigDecimal countOfRatings = BigDecimal.valueOf(count);
        log.debug("getting countOfRatings {}", countOfRatings);

        BigDecimal calculatedRating;
        try {
            calculatedRating = sumOfRatings
                    .divide(countOfRatings)
                    .setScale(2, BigDecimal.ROUND_DOWN);
            log.debug("getting calculatedRating {}", calculatedRating);
        } catch (ArithmeticException e) {
            log.debug("Movie {} is not rated yet", movie);
            calculatedRating = BigDecimal.ZERO;
        }
        localMovie.setRating(calculatedRating.doubleValue());
        return localMovie;
    }

    @Override
    public boolean saveRate(Rate rate) {
        log.debug("saveRate. saving rate = {}", rate);
        for (Rate storredRate : rateQueue) {
            if (storredRate.getMovieId() == rate.getMovieId() && storredRate.getUserId() == rate.getUserId()) {
                rateQueue.remove(storredRate);
            }
        }
        return rateQueue.add(rate);
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
    public RatingToCountPair getRatingToCountPair(int movieId) {
        log.debug("getRatingToCountPair. getting SUM(rating) and count of rated users for movie with id = {}", movieId);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movieId);
        RatingToCountPair pair = null;
        try {
            pair = namedParameterJdbcTemplate.queryForObject(getSumRatingWithCountOfRatedUsersSql, mapSqlParameterSource, RATING_TO_COUNT_OF_RATED_USERS_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            log.debug("Looks like movie with id = {} is not rated yet", movieId);
            pair = new RatingToCountPair(0, 0);
        } finally {
            log.debug("getRatingToCountPair. pair = {}", pair);
            return pair;
        }
    }
}
