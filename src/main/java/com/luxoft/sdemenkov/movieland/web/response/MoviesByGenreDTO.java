package com.luxoft.sdemenkov.movieland.web.response;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;

import java.math.BigDecimal;

public class MoviesByGenreDTO implements Sortable {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private double rating;
    private BigDecimal price;
    private String picturePath;

    public MoviesByGenreDTO() {
    }

    public MoviesByGenreDTO(Movie movie) {
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
        return "MoviesByGenreDTO{" +
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
