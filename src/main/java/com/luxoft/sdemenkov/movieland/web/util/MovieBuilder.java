package com.luxoft.sdemenkov.movieland.web.util;

import com.luxoft.sdemenkov.movieland.model.business.Country;
import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.web.dto.CountryDto;
import com.luxoft.sdemenkov.movieland.web.dto.GenreDto;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MovieBuilder {
    private SaveMovieDto saveMovieDto;
    private Optional<Movie> movie = Optional.empty();
    private Optional<List<Country>> countryList = Optional.empty();
    private Optional<List<Genre>> genreList =Optional.empty();

    public static MovieBuilder fromMovieDto(SaveMovieDto saveMovieDto) {
        MovieBuilder movieBuilder = new MovieBuilder();
        movieBuilder.saveMovieDto = saveMovieDto;
        return movieBuilder;
    }

    public MovieBuilder getMovie(){
        this.movie = Optional.of(new Movie());
        this.movie.get().setNameNative(this.saveMovieDto.getNameNative());
        this.movie.get().setNameRussian(this.saveMovieDto.getNameRussian());
        this.movie.get().setYearOfRelease(this.saveMovieDto.getYearOfRelease());
        this.movie.get().setRating(this.saveMovieDto.getRating());
        this.movie.get().setPrice(new BigDecimal(this.saveMovieDto.getPrice()));
        this.movie.get().setPicturePath(this.saveMovieDto.getPicturePath());
        this.movie.get().setDescription(this.saveMovieDto.getDescription());
        return this;
    }
    public MovieBuilder getCountries() {
        this.countryList = Optional.of(new ArrayList<>());
        for (CountryDto countryDto : this.saveMovieDto.getCountries()) {
            this.countryList.get().add(new Country(countryDto.getId(), countryDto.getName()));
        }
        return this;
    }
    public MovieBuilder getGenres() {
        this.genreList = Optional.of(new ArrayList<>());
        for (GenreDto genreDto : this.saveMovieDto.getGenres()) {
            this.genreList.get().add(new Genre(genreDto.getId(), genreDto.getName()));
        }
        return this;
    }

    public Movie build() {
        this.movie.get().setCountryList(this.countryList.orElse(new ArrayList<>()));
        this.movie.get().setGenreList(this.genreList.orElse(new ArrayList<>()));
        return this.movie.get();
    }


}
