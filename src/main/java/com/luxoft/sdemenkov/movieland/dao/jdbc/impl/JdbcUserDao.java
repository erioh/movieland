package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.UserDao;
import com.luxoft.sdemenkov.movieland.dao.mapper.UserRowMapper;
import com.luxoft.sdemenkov.movieland.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcUserDao implements UserDao {
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private String getUserSQL;

    @Override
    public User getUser(String email, String password) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("email", email);
        mapSqlParameterSource.addValue("password", password);
        List<User> userList = namedParameterJdbcTemplate.query(getUserSQL, mapSqlParameterSource, USER_ROW_MAPPER);
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }
}
