package com.luxoft.sdemenkov.movieland.web.responce;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;

public class AllMoviesDTO implements Sortable {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private double rating;
    private double price;
    private String picturePath;

    public AllMoviesDTO() {
    }

    public AllMoviesDTO(Movie movie) {
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
    public double getPrice() {
        return price;
    }


    public String getPicturePath() {
        return picturePath;
    }


    @Override
    public String toString() {
        return "AllMoviesDTO{" +
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
