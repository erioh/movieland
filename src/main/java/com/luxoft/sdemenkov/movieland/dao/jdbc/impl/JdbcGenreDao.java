package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.GenreRowMapper;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcGenreDao implements GenreDao {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    @Autowired
    private String getGenreWithMappedMovieSQL;
    @Autowired
    private String getAllGenresSQL;
    @Autowired
    private String getGenreListByMovieSQL;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Genre> getGenreListByMove(Movie movie) {
        List<Genre> genreList = jdbcTemplate.query(getGenreListByMovieSQL, new Object[]{movie.getId()}, GENRE_ROW_MAPPER);
        log.debug("Calling method getGenreListByMove. with query = {}", getGenreListByMovieSQL);
        log.debug("Calling method getGenreListByMove with movie_id = {}, ", movie.getId());
        return genreList;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genreList = jdbcTemplate.query(getAllGenresSQL, GENRE_ROW_MAPPER);
        log.debug("Calling method getAllGenres with query {}", getAllGenresSQL);
        return genreList;
    }

    public void enrichMoviesWithGenres(List<Movie> movieList) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        List<Integer> movieIds = new ArrayList<>();
        for (Movie movie : movieList) {
            movieIds.add(movie.getId());
        }
        sqlParameterSource.addValue("ids", movieIds);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(getGenreWithMappedMovieSQL, sqlParameterSource);

        for (Movie movie : movieList) {
            List<Genre> genreList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if ((Integer) map.get("movie_id") == movie.getId()) {
                    Genre genre = new Genre();
                    genre.setId((Integer) map.get("genre_id"));
                    genre.setName((String) map.get("name"));
                    genreList.add(genre);
                }
            }
            movie.setGenreList(genreList);
        }
    }

}
