package com.luxoft.sdemenkov.movieland.web.util;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MovieBuilder {
    private SaveMovieDto saveMovieDto;
    private Movie movie;
    private List<Country> countryList = new ArrayList<>();
    private List<Genre> genreList = new ArrayList<>();

    public static MovieBuilder fromMovieDto(SaveMovieDto saveMovieDto) {
        MovieBuilder movieBuilder = new MovieBuilder();
        movieBuilder.saveMovieDto = saveMovieDto;
        return movieBuilder;
    }

    public MovieBuilder getMovie() {
        this.movie = new Movie();
        this.movie.setId(this.saveMovieDto.getId());
        this.movie.setNameNative(this.saveMovieDto.getNameNative());
        this.movie.setNameRussian(this.saveMovieDto.getNameRussian());
        this.movie.setYearOfRelease(this.saveMovieDto.getYearOfRelease());
        this.movie.setRating(this.saveMovieDto.getRating());
        this.movie.setPrice(new BigDecimal(this.saveMovieDto.getPrice()));
        this.movie.setPicturePath(this.saveMovieDto.getPicturePath());
        this.movie.setDescription(this.saveMovieDto.getDescription());
        return this;
    }

    public MovieBuilder getCountries() {
        if (this.saveMovieDto.getCountries() == null) {
            return this;
        }
        countryList = new ArrayList<>();
        for (Integer countryId : this.saveMovieDto.getCountries()) {
            this.countryList.add(new Country(countryId));
        }
        return this;
    }

    public MovieBuilder getGenres() {
        if (this.saveMovieDto.getGenres() == null) {
            return this;
        }
        for (Integer genreId : this.saveMovieDto.getGenres()) {
            this.genreList.add(new Genre(genreId));
        }
        return this;
    }

    public Movie build() {
        this.movie.setCountryList(this.countryList);
        this.movie.setGenreList(this.genreList);
        return this.movie;
    }


}
