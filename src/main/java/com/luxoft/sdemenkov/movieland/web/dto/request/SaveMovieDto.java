package com.luxoft.sdemenkov.movieland.web.dto.request;


import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.CountryDto;
import com.luxoft.sdemenkov.movieland.web.dto.GenreDto;

import java.util.ArrayList;
import java.util.List;

public class SaveMovieDto {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<CountryDto> countries;
    private List<GenreDto> genres;

    public SaveMovieDto() {
    }

    public SaveMovieDto(Movie movie) {
        this.id = movie.getId();
        this.nameRussian = movie.getNameRussian();
        this.nameNative = movie.getNameNative();
        this.yearOfRelease = movie.getYearOfRelease();
        this.description = movie.getDescription();
        this.rating = movie.getRating();
        this.price = movie.getId();
        this.picturePath = movie.getPicturePath();
        this.countries = new ArrayList<>();
        for (Country country : movie.getCountryList()) {
            this.countries.add(new CountryDto(country));
        }
        this.genres = new ArrayList<>();
        for (Genre genre : movie.getGenreList()) {
            this.genres.add(new GenreDto(genre));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRussian() {
        return nameRussian;
    }

    public void setNameRussian(String nameRussian) {
        this.nameRussian = nameRussian;
    }

    public String getNameNative() {
        return nameNative;
    }

    public void setNameNative(String nameNative) {
        this.nameNative = nameNative;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public List<CountryDto> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDto> countries) {
        this.countries = countries;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    @Override
    public String toString() {
        return "SaveMovieDto{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
