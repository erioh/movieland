package com.luxoft.sdemenkov.movielend.dao.mappers;

import com.luxoft.sdemenkov.movielend.models.Country;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CountryRowMapperTest {
    @Test
    public void mapRow() throws Exception {

        // Mocking objects
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("Tester");

        // Test
        CountryRowMapper countryRowMapper = new CountryRowMapper();
        Country country = countryRowMapper.mapRow(resultSet, 0);
        assertEquals(1, country.getId());
        assertEquals("Tester", country.getName());

    }

}