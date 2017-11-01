package com.luxoft.sdemenkov.movieland.dao.mapper;

import com.luxoft.sdemenkov.movieland.model.Movie;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieRowMapperTest {
    @Test
    public void mapRow() throws Exception {

        // Mocking objects
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt(anyString()))
                .thenReturn(1)
                .thenReturn(1994);
        when(resultSet.getString(anyString()))
                .thenReturn("Побег из Шоушенка")
                .thenReturn("The Shawshank Redemption")
                .thenReturn("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg")
                .thenReturn("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.");
        when(resultSet.getDouble(anyString()))
                .thenReturn(8.9)
                .thenReturn(123.45);

        // Test
        MovieRowMapper movieRowMapper = new MovieRowMapper();
        Movie movie = movieRowMapper.mapRow(resultSet, 0);
        assertEquals(movie.getId(), 1);
        assertEquals("Побег из Шоушенка", movie.getNameRussian());
        assertEquals("The Shawshank Redemption", movie.getNameNative());
        assertEquals("https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg", movie.getPicturePath());
        assertEquals("Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.", movie.getDescription());
        assertEquals(8.9, movie.getRating(), 0);
        assertEquals(123.45, movie.getPrice(), 0);

    }

}