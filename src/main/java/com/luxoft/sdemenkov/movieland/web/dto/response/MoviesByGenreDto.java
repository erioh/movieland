package com.luxoft.sdemenkov.movieland.web.dto.response;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;

import java.math.BigDecimal;

public class MoviesByGenreDto implements Sortable {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private double rating;
    private BigDecimal price;
    private String picturePath;

    public MoviesByGenreDto() {
    }

    public MoviesByGenreDto(Movie movie) {
        id = movie.getId();
        nameRussian = movie.getNameRussian();
        nameNative = movie.getNameNative();
        yearOfRelease = movie.getYearOfRelease();
        rating = movie.getRating();
        price = movie.getPrice();
        picturePath = movie.getPicturePath();
    }

    public int getId() {
        return id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }


    public int getYearOfRelease() {
        return yearOfRelease;
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }


    public String getPicturePath() {
        return picturePath;
    }


    @Override
    public String toString() {
        return "MoviesByGenreDto{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                '}';
    }
}
