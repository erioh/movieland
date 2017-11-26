package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.CountryDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.CountryRowMapper;
import com.luxoft.sdemenkov.movieland.model.business.Country;
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
    @Autowired
    private String movieIdCountryMapperSQL;
    @Autowired
    private String removeMappedCountriesSQL;


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
    public List<Country> getAll() {
        List<Country> countryList = jdbcTemplate.query(getAllCountriesSQL, COUNTRY_ROW_MAPPER);
        log.debug("Result of getAllCountriesSQL is {}", countryList);
        return countryList;
    }

    @Override
    public int[] mapMoviesCountry(Movie movie) {
        log.debug("mapMoviesCountry. is called for movie {}", movie);
        removeMappedCountries(movie);
        int movieId = movie.getId();
        List<MovieIdCountryMapper> movieIdCountryMapperList = new ArrayList<>();
        for (Country country : movie.getCountryList()) {
            movieIdCountryMapperList.add(new MovieIdCountryMapper(movieId, country.getId()));
        }
        SqlParameterSource[] sqlParameterSources = SqlParameterSourceUtils.createBatch(movieIdCountryMapperList.toArray());
        return namedParameterJdbcTemplate.batchUpdate(movieIdCountryMapperSQL, sqlParameterSources);
    }

    @Override
    public int removeMappedCountries(Movie movie) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("movieId", movie.getId());
        return namedParameterJdbcTemplate.update(removeMappedCountriesSQL, mapSqlParameterSource);
    }

    private class MovieIdCountryMapper {
        private int movieId;
        private int countryId;

        public MovieIdCountryMapper(int movieId, int countryId) {
            this.movieId = movieId;
            this.countryId = countryId;
        }

        public int getMovieId() {
            return movieId;
        }

        public void setMovieId(int movieId) {
            this.movieId = movieId;
        }

        public int getCountryId() {
            return countryId;
        }

        public void setCountryId(int countryId) {
            this.countryId = countryId;
        }
    }
}
