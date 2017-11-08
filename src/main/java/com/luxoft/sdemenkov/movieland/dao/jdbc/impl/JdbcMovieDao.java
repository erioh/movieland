package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.MovieRowMapper;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Repository
public class JdbcMovieDao implements MovieDao {


    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final Random randomGenerator = new Random();
    @Autowired
    private String getMoviesByGenreIdSQL;
    @Autowired
    private String getAllMoviesSQL;
    @Autowired
    private String getCountOfMoviesSQL;
    @Autowired
    private String getMovieByIdSQL;


    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = jdbcTemplate.query(getAllMoviesSQL, movieRowMapper);
        log.debug("Calling method getAllMovies. with query = {}", getAllMoviesSQL);
        log.debug("Calling method getAllMovies. Result = {}", movieList);
        return movieList;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        Set<Integer> ids = new HashSet<>();
        int countOfRows = getCountOfMovies();
        int searchedCount = countOfRows >= 3 ? 3 : countOfRows;
        while (ids.size() != searchedCount) {
            ids.add(randomGenerator.nextInt(countOfRows + 1));
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
        List<Movie> movie = namedParameterJdbcTemplate.query(getMovieByIdSQL, sqlParameterSource, movieRowMapper);
        log.debug("Movie with id = {} was selected. Movie = {}", ids, movie.get(0));
        return movie;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> movieList = jdbcTemplate.query(getMoviesByGenreIdSQL, new Object[]{genreId}, movieRowMapper);
        log.debug("Method getMoviesByGenre is called with sql = {} and genre_id = {} ", getMoviesByGenreIdSQL, genreId);
        log.debug("Method getMoviesByGenre is called. Result is {}", movieList);
        return movieList;
    }
}
