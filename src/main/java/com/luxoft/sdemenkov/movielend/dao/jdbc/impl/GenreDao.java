package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.dao.mapper.GenreRowMapper;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String GET_ALL_GENRES_SQL = "select genre_id, name from genre;";

    private static final String GET_GENRE_LIST_BY_MOVIE_SQL = "select g.genre_id, g.name from genre g " +
            "join movie_genre mg " +
            "on g.genre_id = mg.genre_id " +
            "where mg.movie_id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Genre> getGenreListByMove(Movie movie) {
        List<Genre> genreList = jdbcTemplate.query(GET_GENRE_LIST_BY_MOVIE_SQL, new Object[]{movie.getId()}, new GenreRowMapper());
        log.info("Calling method getGenreListByMove. with query = {}", GET_GENRE_LIST_BY_MOVIE_SQL);
        log.info("Calling method getGenreListByMove with movie_id = {}, ", movie.getId());
        return genreList;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genreList = jdbcTemplate.query(GET_ALL_GENRES_SQL, new GenreRowMapper());
        log.info("Calling method getAllGenres with query {}", GET_ALL_GENRES_SQL);
        return genreList;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
