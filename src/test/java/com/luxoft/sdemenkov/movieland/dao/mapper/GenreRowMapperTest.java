package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.Genre;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenreRowMapperTest {
    @Test
    public void mapRow() throws Exception {

        // Mocking objects
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyString())).thenReturn(12);
        when(resultSet.getString(anyString())).thenReturn("name");

        // Test
        GenreRowMapper genreRowMapper = new GenreRowMapper();
        Genre actualGenre = genreRowMapper.mapRow(resultSet, 0);
        assertEquals(12, actualGenre.getId());
        assertEquals("name", actualGenre.getName());
    }

}