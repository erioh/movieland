package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
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