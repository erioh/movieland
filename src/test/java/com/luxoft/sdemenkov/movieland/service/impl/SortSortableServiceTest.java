package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllMovies;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SortSortableServiceTest {
    @Test
    public void sortByPriceAsc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = new Movie();
        Movie movieMin = new Movie();
        movieMax.setPrice(10);
        movieMin.setPrice(8);
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMin));
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, "asc");
        assertEquals(8, responseGetAllMoviesList.get(0).getPrice(), 0);
    }

    @Test
    public void sortByPriceDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = new Movie();
        Movie movieMin = new Movie();
        movieMax.setPrice(10);
        movieMin.setPrice(8);
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMin));
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, "desc");
        assertEquals(10, responseGetAllMoviesList.get(0).getPrice(), 0);
    }

    @Test
    public void sortByRating() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = new Movie();
        Movie movieMin = new Movie();
        movieMax.setRating(10);
        movieMin.setRating(8);
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMin));
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByRating(responseGetAllMoviesList, "desc");
        assertEquals(10, responseGetAllMoviesList.get(0).getRating(), 0);
    }

}