package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.security.role.Role;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RoleRowMapperTest {
    @Test
    public void mapRow() throws Exception {
        // Mocking objects
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getString("role")).thenReturn("USER");
        RoleRowMapper roleRowMapper = new RoleRowMapper();
        Role role = roleRowMapper.mapRow(resultSet, 0);
        assertEquals(Role.USER, role);
    }

}