package com.luxoft.sdemenkov.movieland.dao.jdbc.impl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueryBuilderTest {
    @Test
    public void builderTest() throws Exception {
        String query = "select 1 from dual;";
        String result = QueryBuilder.forQuery(query).withLimit(2, 5).build();
        assertEquals("select 1 from dual LIMIT 5 , 5 ;", result);
    }

}