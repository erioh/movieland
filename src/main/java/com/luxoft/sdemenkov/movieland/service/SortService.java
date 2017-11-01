package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.Movie;

import java.util.List;

public interface SortService<E> {
    List<E> sort(List<E> list, String expression);

}
