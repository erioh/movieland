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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AccurateJdbcMovieDao implements MovieDao {
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
    @Autowired
    private String getStoredRatingsSql;

    private Queue<Rate> rateQueue = new ConcurrentLinkedQueue<>();

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
        BigDecimal newRating = rating.divide(count, BigDecimal.ROUND_HALF_DOWN).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        double result = newRating.doubleValue();

        log.debug("recalculateRateForMovieInacurate. result of caclulation is {} for pair {}", result, pair);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("newRating", result);
        mapSqlParameterSource.addValue("movieId", movieId);

        int countOfChangedRows = namedParameterJdbcTemplate.update(inAccurateUpdateRateForMovieSql, mapSqlParameterSource);
        if (countOfChangedRows == 0) {
            log.warn("Movie with id = {} is not present in DB. Rating is not added", movieId);
            throw new WrongMovieIdException(movieId);
        }

        return result;
    }

    public double recalculateRateForMovieAcurate(int movieId, RatingToCountPair pair) {
        log.debug("recalculateRateForMovieInacurate. recalculating rating for movie with id = {} with pair = {}", movieId, pair);

        BigDecimal rating = BigDecimal.valueOf(pair.getRatingSum());
        BigDecimal count = BigDecimal.valueOf(pair.getCount());
        BigDecimal newRating = rating.divide(count, BigDecimal.ROUND_HALF_DOWN).setScale(2, BigDecimal.ROUND_HALF_DOWN);

        double result = newRating.doubleValue();

        log.debug("recalculateRateForMovieInacurate. result of caclulation is {} for pair {}", result, pair);

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("newRating", result);
        mapSqlParameterSource.addValue("movieId", movieId);

        int countOfChangedRows = namedParameterJdbcTemplate.update(updateRateForMovieSql, mapSqlParameterSource);
        if (countOfChangedRows == 0) {
            log.warn("Movie with id = {} is not present in DB. Rating is not added", movieId);
            throw new WrongMovieIdException(movieId);
        }

        return result;
    }




    @Override
    @Scheduled(fixedDelayString = "${cron.dao.rates.buffered.flush}", initialDelayString = "${cron.dao.rates.buffered.flush}")
    @Transactional
    @PreDestroy
    public int flush() {
        log.debug("Flushing rates. List if rates = {}", rateQueue);
        int count = 0;
        for (Rate rate : rateQueue) {
            try {
                deleteUsersRateForMovie(rate.getUserId(), rate.getMovieId());

                MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
                mapSqlParameterSource.addValue("movieId", rate.getMovieId());
                mapSqlParameterSource.addValue("userId", rate.getUserId());
                mapSqlParameterSource.addValue("rating", rate.getRating());
                namedParameterJdbcTemplate.update(saveRateSql, mapSqlParameterSource);

                RatingToCountPair pair = getRatingToCountPair(rate.getMovieId());

                recalculateRateForMovie(rate.getMovieId(), pair);
            } finally {
                rateQueue.remove(rate);
            }
            count++;
        }
        return count;
    }

    @Override
    public void enrichMovieWithActualRates(Movie movie) {
        log.info("Starting enrich with actual rating");
        log.debug("Getting stored in DB ratings for movie {}", movie);
        Map<Integer, Rate> userIdToRateMap = getStoredRatings(movie);

        log.debug("Removing not actual stored rates");
        for (Rate rate : rateQueue) {
            Rate remove = userIdToRateMap.remove(rate.getUserId());
            log.debug("Rate {} is not actual and is removed from calculation process", remove);
        }

        log.debug("Getting sum of stored filtered rates and count of users");
        int count = 0;
        double rating = 0;
        for (Map.Entry<Integer, Rate> userIdToRateEntry : userIdToRateMap.entrySet()) {
            count++;
            rating += userIdToRateEntry.getValue().getRating();
        }
        log.debug("Adding not stored ratings {}", rateQueue);
        for (Rate rate : rateQueue) {
            if (rate.getMovieId() == movie.getId()) {
                count++;
                rating += rate.getRating();
            }
        }

        log.debug("Calculating ratings AVG");
        BigDecimal sumOfRatings = BigDecimal.valueOf(rating);
        BigDecimal countOfRatings = BigDecimal.valueOf(count);
        BigDecimal calculatedRating = sumOfRatings
                .divide(countOfRatings, BigDecimal.ROUND_HALF_DOWN)
                .setScale(2, BigDecimal.ROUND_HALF_DOWN);
        movie.setRating(calculatedRating.doubleValue());
    }

    public Map<Integer, Rate> getStoredRatings(Movie movie) {
        Map<Integer, Rate> storedRatingMap = new HashMap<>();
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movie.getId());
        List<Rate> rateList = namedParameterJdbcTemplate.query(getStoredRatingsSql, mapSqlParameterSource, USER_TO_RATE_ROW_MAPPER);
        for (Rate rate : rateList) {
            storedRatingMap.put(rate.getUserId(), rate);
        }
        return storedRatingMap;

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
        RatingToCountPair pair = namedParameterJdbcTemplate.queryForObject(getSumRatingWithCountOfRatedUsersSql, mapSqlParameterSource, RATING_TO_COUNT_OF_RATED_USERS_ROW_MAPPER);
        log.debug("getRatingToCountPair. pair = {}", pair);
        return pair;
    }
}
