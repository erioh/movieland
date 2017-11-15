package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

public interface CurrencyExchangeService {

    List<Movie> getMovieWithChangedCurrency(List<Movie> movieList, Currency currency);

}
