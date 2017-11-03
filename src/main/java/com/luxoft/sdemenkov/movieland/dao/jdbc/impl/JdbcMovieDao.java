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

import java.util.*;

@Repository
public class JdbcMovieDao implements MovieDao {


    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();
    private final Random randomGenerator = new Random();

//    private static final String GET_MOVIES_BY_GENRE_ID_SQL = "select m.movie_id, m.name_russian, m.name_native,  " +
//            "m.year_of_release, m.description, m.rating, m.price, p.picture_path from movie m " +
//            "inner join movie_poster mp on m.movie_id = mp.movie_id " +
//            "inner join poster p on mp.picture_id = p.picture_id " +
//            "inner join movie_genre mg on m.movie_id = mg.movie_id " +
//            "where mg.genre_id = ? ";
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
        Set<Integer> ids = new HashSet<>();
        int countOfRows = getCountOfMovies();
        int searchedCount = countOfRows >= 3 ? 3 : countOfRows;
        while ( ids.size() != searchedCount){
            ids.add(randomGenerator.nextInt(countOfRows));
        }
        List<Movie> movieList = getMovieListByIds(ids);
        for (int i = 0; i < 3; i++) {
            if (movieList.size() < 3)
                movieList.add(movieList.get(0));
        }
        return movieList;
    }

    @Override
    public int getCountOfMovies() {
        int countOfMovies = jdbcTemplate.queryForObject(GET_COUNT_OF_MOVIES, Integer.class);
        log.debug("Count of movies = {}", countOfMovies);
        return countOfMovies;
    }

    @Override
    public List<Movie> getMovieListByIds(Set<Integer> ids) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        sqlParameterSource.addValue("ids", ids);
        List<Movie> movie = namedParameterJdbcTemplate.query(GET_MOVIE_BY_ID_SQL, sqlParameterSource, movieRowMapper);
        log.debug("Movie with id = {} was selected. Movie = {}", ids, movie.get(0));
        return movie;
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        List<Movie> movieList = jdbcTemplate.query(GET_MOVIES_BY_GENRE_ID_SQL, new Object[]{genreId}, movieRowMapper);
        log.debug("Method getMoviesByGenre is called with sql = {} and genre_id = {} ", GET_MOVIES_BY_GENRE_ID_SQL, genreId);
        log.debug("Method getMoviesByGenre is called. Result is {}", movieList);
        return movieList;
    }
}
