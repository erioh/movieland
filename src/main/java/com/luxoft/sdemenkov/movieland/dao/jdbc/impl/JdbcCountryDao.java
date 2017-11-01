package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.jdbc.CountryDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.CountryRowMapper;
import com.luxoft.sdemenkov.movieland.model.Country;
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
public class JdbcCountryDao implements CountryDao {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String GET_COUNTRY_WITH_MAPPED_MOVIE_ID_SQL = "select mc.movie_id, c.country_id, c.name from country c " +
            "inner join movie_country mc " +
            "on mc.country_id = c.country_id " +
            "where mc.movie_id in (:ids)";
    private static final String GET_CONTRY_BY_MOVIE_SQL = "select c.country_id, " +
            "c.name from country c " +
            "join movie_country mc " +
            "on c.country_id = mc.country_id  " +
            "where movie_id = ?;";

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private CountryRowMapper countryRowMapper = new CountryRowMapper();

    public List<Country> getCountryListByMovie(Movie movie) {
        List<Country> countryList = jdbcTemplate.query(GET_CONTRY_BY_MOVIE_SQL, new Object[]{movie.getId()}, countryRowMapper);
        log.debug("Calling method getCountryListByMovie. with query = {}", GET_CONTRY_BY_MOVIE_SQL);
        log.debug("Calling method getCountryListByMovie with movie_id = {}", movie.getId());
        return countryList;

    }

    public List<Movie> enrichMoviesByCountries(List<Movie> movieList) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        List<Integer> movieIds = new ArrayList<>();
        for (Movie movie : movieList) {
            movieIds.add(movie.getId());
        }
        sqlParameterSource.addValue("ids", movieIds);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(GET_COUNTRY_WITH_MAPPED_MOVIE_ID_SQL, sqlParameterSource);

        for (Movie movie : movieList) {
            List<Country> countryList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                if ((Integer) map.get("movie_id") == movie.getId()) {
                    Country country = new Country();
                    country.setId((Integer) map.get("country_id"));
                    country.setName((String) map.get("name"));
                    countryList.add(country);
                }
            }
            movie.setCountryList(countryList);
        }
        return movieList;
    }

}
