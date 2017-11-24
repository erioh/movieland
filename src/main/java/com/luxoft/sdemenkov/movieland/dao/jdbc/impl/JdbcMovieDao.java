package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.MovieRowMapper;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Repository
public class JdbcMovieDao implements MovieDao {
    private final static MovieRowMapper MOVIE_ROW_MAPPER = new MovieRowMapper();
    private final static Random RANDOM_GENERATOR = new Random();
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
    @Transactional
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
    public Movie set(Movie movie) {
        return null;
    }
}
