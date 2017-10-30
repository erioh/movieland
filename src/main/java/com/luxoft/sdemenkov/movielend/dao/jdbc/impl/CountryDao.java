package com.luxoft.sdemenkov.movielend.dao.jdbc.impl;

import com.luxoft.sdemenkov.movielend.dao.mapper.CountryRowMapper;
import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CountryDao {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_CONTRY_BY_MOVIE_SQL = "select c.country_id, " +
            "c.name from country c " +
            "join movie_country mc " +
            "on c.country_id = mc.country_id  " +
            "where movie_id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Country> getCountryListByMovie(Movie movie) {
        List<Country> countryList = jdbcTemplate.query(GET_CONTRY_BY_MOVIE_SQL, new Object[]{movie.getId()}, new CountryRowMapper());
        log.debug("Calling method getCountryListByMovie. with query = {}", GET_CONTRY_BY_MOVIE_SQL);
        log.debug("Calling method getCountryListByMovie with movie_id = {}", movie.getId());
        return countryList;

    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
