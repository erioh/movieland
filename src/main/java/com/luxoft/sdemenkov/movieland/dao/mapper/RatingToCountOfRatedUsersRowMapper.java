package com.luxoft.sdemenkov.movieland.dao.mapper;



import com.luxoft.sdemenkov.movieland.model.technical.RatingToCountPair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingToCountOfRatedUsersRowMapper implements RowMapper<RatingToCountPair> {
    @Override
    public RatingToCountPair mapRow(ResultSet rs, int rowNum) throws SQLException {
        double rating = rs.getDouble("ratingSum");
        int count = rs.getInt("cnt");
        return new RatingToCountPair(rating, count);
    }
}
