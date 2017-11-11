package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreRowMapper implements RowMapper<Genre> {
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre(rs.getInt("genre_id"),rs.getString("name"));
        return genre;
    }
}
