package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.business.Movie;

import java.util.List;

public interface MovieEnrichmentService {
    void enrich(List<Movie> movieList);
}