package com.luxoft.sdemenkov.movielend.web.responce;

import com.luxoft.sdemenkov.movielend.model.Country;
import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;

import java.util.List;

public class ResponceGetThreeRandomMovies {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private double price;
    private String picturePath;
    private List<Country> countryList;
    private List<Genre> genreList;

    public ResponceGetThreeRandomMovies(Movie movie) {
        this.id = movie.getId();
        this.nameRussian = movie.getNameRussian();
        this.nameNative = movie.getNameNative();
        this.yearOfRelease = movie.getYearOfRelease();
        this.description = movie.getDescription();
        this.rating = movie.getRating();
        this.price = movie.getPrice();
        this.picturePath = movie.getPicturePath();
        this.countryList = movie.getCountryList();
        this.genreList = movie.getGenreList();
    }

    public ResponceGetThreeRandomMovies() {
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

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public List<Genre> getGenreList() {
        return genreList;
    }

    public void setGenreList(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @Override
    public String toString() {
        return "ResponceGetThreeRandomMovies{" +
                "id=" + id +
                ", nameRussian='" + nameRussian + '\'' +
                ", nameNative='" + nameNative + '\'' +
                ", yearOfRelease=" + yearOfRelease +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", picturePath='" + picturePath + '\'' +
                ", countryList=" + countryList +
                ", genreList=" + genreList +
                '}';
    }
}
