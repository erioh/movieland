package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.dao.mapper.MovieRowMapper;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class MovieDaoImpl implements MovieDao{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final Random randomGenerator = new Random();

    private static final String GET_ALL_MOVIES_SQL = "select m.movie_id, m.name_russian, m.name_native,  " +
            "m.year_of_release, m.description, m.rating, m.price, p.picture_path from movie m " +
            "inner join movie_poster mp on m.movie_id = mp.movie_id " +
            "inner join poster p on mp.picture_id = p.picture_id";
    private static final String GET_COUNT_OF_MOVIES = "select count(*) from movie;";
    private static final String GET_MOVIE_BY_ID_SQL = "select m.movie_id, m.name_russian, m.name_native,  " +
            "m.year_of_release, m.description, m.rating, m.price, p.picture_path from movie m " +
            "inner join movie_poster mp on m.movie_id = mp.movie_id " +
            "inner join poster p on mp.picture_id = p.picture_id " +
            "where m.movie_id in (:ids)";

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movieList = jdbcTemplate.query(GET_ALL_MOVIES_SQL, movieRowMapper);
        log.debug("Calling method getAllMovies. with query = {}", GET_ALL_MOVIES_SQL);
        log.debug("Calling method getAllMovies. Result = {}", movieList);
        return movieList;
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        List<Integer> ids = new ArrayList<>();
        int countOfRows = getCountOfMovies();
        for (int i = 0; i < 3; i++) {
            ids.add(randomGenerator.nextInt(countOfRows));
        }
        List<Movie> movieList = getMovieListByIds(ids);
        return movieList;
    }
    @Override
    public int getCountOfMovies() {
        int countOfMovies = jdbcTemplate.queryForObject(GET_COUNT_OF_MOVIES, Integer.class);
        log.debug("Count of movies = {}", countOfMovies);
        return countOfMovies;
    }

    @Override
    public List<Movie> getMovieListByIds(List<Integer> ids) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("ids", ids);
        List<Movie> movie = namedParameterJdbcTemplate.query(GET_MOVIE_BY_ID_SQL, sqlParameterSource, movieRowMapper);
        log.debug("Movie with id = {} was selected. Movie = {}", ids, movie.get(0));
        return movie;
    }

}
