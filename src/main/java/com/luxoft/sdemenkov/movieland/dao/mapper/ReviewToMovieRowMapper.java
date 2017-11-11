package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.Review;
import com.luxoft.sdemenkov.movieland.model.User;
import javafx.util.Pair;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewToMovieRowMapper implements RowMapper<Pair<Integer, Review>> {
    @Override
    public Pair<Integer, Review> mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setNickname(resultSet.getString("user_name"));

        Review review = new Review();
        review.setId(resultSet.getInt("review_id"));
        review.setText(resultSet.getString("text"));
        review.setUser(user);

        int movieId = resultSet.getInt("movie_id");

        return (Pair<Integer, Review>) new Pair(movieId, review);
    }
}
