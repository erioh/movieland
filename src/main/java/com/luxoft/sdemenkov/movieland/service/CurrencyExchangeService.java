package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

/**
 * Created by sergeydemenkov on 04.11.17.
 */
public interface CurrencyExchangeService {

    List<Movie> getMovieWithChangedCurrency(List<Movie> movieList, Currency currency);

}
