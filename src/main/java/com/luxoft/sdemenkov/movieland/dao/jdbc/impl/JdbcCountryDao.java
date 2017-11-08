package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
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
    private final static CountryRowMapper COUNTRY_ROW_MAPPER = new CountryRowMapper();
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getCountryWithMappedMovieIdSQL;
    @Autowired
    private String getCountryByMovieIdSQL;
    @Autowired
    private String getAllCountriesSQL;


    public List<Country> getCountryListByMovie(Movie movie) {
        List<Country> countryList = jdbcTemplate.query(getCountryByMovieIdSQL, new Object[]{movie.getId()}, COUNTRY_ROW_MAPPER);
        log.debug("Calling method getCountryListByMovie. with query = {}", getCountryByMovieIdSQL);
        log.debug("Calling method getCountryListByMovie with movie_id = {}", movie.getId());
        return countryList;

    }

    public void enrichMoviesWithCountries(List<Movie> movieList) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
        List<Integer> movieIds = new ArrayList<>();
        for (Movie movie : movieList) {
            movieIds.add(movie.getId());
        }
        sqlParameterSource.addValue("ids", movieIds);
        List<Map<String, Object>> list = namedParameterJdbcTemplate.queryForList(getCountryWithMappedMovieIdSQL, sqlParameterSource);

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
    }

    @Override
    public List<Country> getAllCountries() {
        List<Country> countryList = jdbcTemplate.query(getAllCountriesSQL, COUNTRY_ROW_MAPPER);
        return countryList;
    }
}
