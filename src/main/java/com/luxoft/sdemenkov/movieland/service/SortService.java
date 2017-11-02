package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;

import java.util.List;


public interface SortService {
    List<Sortable> sortByRating(List<Sortable> list, String expression);
    List<Sortable> sortByPrice(List<Sortable> list, String expression);

}
