package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.jdbc.GenreDao;
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

    private static final String GET_GENRE_WITH_MAPPED_MOVIE_ID_SQL = "select mg.movie_id, g.genre_id, g.name " +
            "from genre g inner join movie_genre mg on g.genre_id = mg.genre_id " +
            "where mg.movie_id in (:ids);";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String GET_ALL_GENRES_SQL = "select genre_id, name from genre;";

    private static final String GET_GENRE_LIST_BY_MOVIE_SQL = "select g.genre_id, g.name from genre g " +
            "join movie_genre mg " +
            "on g.genre_id = mg.genre_id " +
            "where mg.movie_id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private GenreRowMapper genreRowMapper = new GenreRowMapper();

    public List<Genre> getGenreListByMove(Movie movie) {
        List<Genre> genreList = jdbcTemplate.query(GET_GENRE_LIST_BY_MOVIE_SQL, new Object[]{movie.getId()}, genreRowMapper);
        log.debug("Calling method getGenreListByMove. with query = {}", GET_GENRE_LIST_BY_MOVIE_SQL);
        log.debug("Calling method getGenreListByMove with movie_id = {}, ", movie.getId());
        return genreList;
    }

    public List<Genre> getAllGenres() {
        List<Genre> genreList = jdbcTemplate.query(GET_ALL_GENRES_SQL, genreRowMapper);
        log.debug("Calling method getAllGenres with query {}", GET_ALL_GENRES_SQL);
        return genreList;
    }

    public List<Movie> enrichMoviesByGenres(List<Movie> movieList) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        List<Integer> movieIds = new ArrayList<>();
        for (Movie movie : movieList) {
            movieIds.add(movie.getId());
        }
        sqlParameterSource.addValue("ids", movieIds);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(GET_GENRE_WITH_MAPPED_MOVIE_ID_SQL, sqlParameterSource);

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
        return movieList;
    }

}
