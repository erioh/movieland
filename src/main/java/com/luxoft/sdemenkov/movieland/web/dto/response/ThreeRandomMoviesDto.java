package com.luxoft.sdemenkov.movieland.web.dto.response;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.dto.CountryDto;
import com.luxoft.sdemenkov.movieland.web.dto.GenreDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ThreeRandomMoviesDto implements Sortable {
    private int id;
    private String nameRussian;
    private String nameNative;
    private int yearOfRelease;
    private String description;
    private double rating;
    private BigDecimal price;
    private String picturePath;
    private List<CountryDto> countries = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();

    public ThreeRandomMoviesDto(Movie movie) {
        this.id = movie.getId();
        this.nameRussian = movie.getNameRussian();
        this.nameNative = movie.getNameNative();
        this.yearOfRelease = movie.getYearOfRelease();
        this.description = movie.getDescription();
        this.rating = movie.getRating();
        this.price = movie.getPrice();
        this.picturePath = movie.getPicturePath();
        for (Country country : movie.getCountryList()) {
            this.countries.add(new CountryDto(country));
        }
        for (Genre genre : movie.getGenreList()) {
            this.genres.add(new GenreDto(genre));
        }
    }

    public ThreeRandomMoviesDto() {
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

    public String getDescription() {
        return description;
    }

    public double getRating() {
        return rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public List<CountryDto> getCountries() {
        return countries;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        return "ThreeRandomMoviesDto{" +
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
