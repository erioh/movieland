package com.luxoft.sdemenkov.movielend.dao.mappers;

import com.luxoft.sdemenkov.movielend.models.Country;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryRowMapper implements RowMapper<Country> {
    public Country mapRow(ResultSet rs, int rowNum) throws SQLException {
        Country country = new Country();
        country.setId(rs.getInt("country_id"));
        country.setName(rs.getString("name"));
        return country;
    }
}
