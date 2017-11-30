package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieRowMapper implements RowMapper<Movie> {

    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();
        movie.setId(rs.getInt("movie_id"));
        movie.setNameRussian(rs.getString("name_Russian"));
        movie.setNameNative(rs.getString("name_Native"));
        movie.setYearOfRelease(rs.getInt("year_of_release"));
        movie.setPicturePath(rs.getString("picture_path"));
        movie.setDescription(rs.getString("description"));
        movie.setRating(rs.getDouble("rating"));
        movie.setPrice(rs.getBigDecimal("price"));
        movie.setNumberOfRates(rs.getInt("number_of_rates"));

        return movie;
    }
}
