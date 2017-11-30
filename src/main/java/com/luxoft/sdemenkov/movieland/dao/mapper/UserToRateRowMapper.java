package com.luxoft.sdemenkov.movieland.dao.mapper;


import com.luxoft.sdemenkov.movieland.model.business.Rate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserToRateRowMapper implements RowMapper<Rate> {
    @Override
    public Rate mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rate rate = new Rate();
        rate.setRating(rs.getDouble("rating"));
        rate.setMovieId(rs.getInt("movie_id"));
        rate.setUserId(rs.getInt("user_id"));
        return rate;
    }
}
