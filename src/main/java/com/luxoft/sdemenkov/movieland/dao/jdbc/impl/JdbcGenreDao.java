package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.GenreRowMapper;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
public class JdbcGenreDao implements GenreDao {

    private final static GenreRowMapper GENRE_ROW_MAPPER = new GenreRowMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());
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
    @Autowired
    private String movieIdGenreMapperSQL;

    public List<Genre> getGenreListByMove(Movie movie) {
        List<Genre> genreList = jdbcTemplate.query(getGenreListByMovieSQL, new Object[]{movie.getId()}, GENRE_ROW_MAPPER);
        log.debug("Calling method getGenreListByMove. with query = {}", getGenreListByMovieSQL);
        log.debug("Calling method getGenreListByMove with movie_id = {}, ", movie.getId());
        return genreList;
    }

    public List<Genre> getAll() {
        List<Genre> genreList = jdbcTemplate.query(getAllGenresSQL, GENRE_ROW_MAPPER);
        log.debug("Calling method getAll with query {}", getAllGenresSQL);
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
                    Genre genre = new Genre((Integer) map.get("genre_id"), (String) map.get("name"));
                    genreList.add(genre);
                }
            }
            movie.setGenreList(genreList);
        }
    }

    @Override
    @Transactional
    public void mapMoviesGenre(Movie movie) {
        log.debug("mapMoviesGenre. mapping genres to movie");
        int movieId = movie.getId();
        List<MovieIdGenreMapper> movieIdGenreMapList = new ArrayList<>();
        for (Genre genre : movie.getGenreList()) {
            movieIdGenreMapList.add(new MovieIdGenreMapper(movieId, genre.getId()));
        }
        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(movieIdGenreMapList.toArray());
        namedParameterJdbcTemplate.batchUpdate(movieIdGenreMapperSQL, sqlParameterSources);
    }

    private class MovieIdGenreMapper {
        private int movieId;
        private int genreId;

        public MovieIdGenreMapper(int movieId, int genreId) {
            this.movieId = movieId;
            this.genreId = genreId;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getGenreId() {
            return genreId;
        }

        public void setGenreId(int genreId) {
            this.genreId = genreId;
        }
    }

}
