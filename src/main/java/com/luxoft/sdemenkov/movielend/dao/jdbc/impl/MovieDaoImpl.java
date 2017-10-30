package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.dao.jdbc.MovieDao;
import com.luxoft.sdemenkov.movielend.dao.mapper.MovieRowMapper;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao{

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private MovieRowMapper movieRowMapper = new MovieRowMapper();

    private static final String GET_ALL_MOVIES_SQL = "select m.movie_id, m.name_russian, m.name_native,  " +
            "m.year_of_release, m.description, m.rating, m.price, p.picture_path from movie m " +
            "inner join movie_poster mp on m.movie_id = mp.movie_id " +
            "inner join poster p on mp.picture_id = p.picture_id";


    public List<Movie> getAllMovies() {
        List<Movie> movieList = jdbcTemplate.query(GET_ALL_MOVIES_SQL, movieRowMapper);
        log.debug("Calling method getAllMovies. with query = {}", GET_ALL_MOVIES_SQL);
        log.debug("Calling method getAllMovies. Result = {}", movieList);
        return movieList;
    }

}
