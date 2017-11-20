package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.business.Currency;
import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;

public interface CurrencyExchangeService {

    List<Movie> getMovieWithChangedCurrency(List<Movie> movieList, Currency currency);

}
