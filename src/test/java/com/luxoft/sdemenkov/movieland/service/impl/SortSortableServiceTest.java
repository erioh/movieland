package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.AllMoviesDTO;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortSortableServiceTest {
    @Test
    public void sortByPriceAsc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("price", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("price", "8");
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMax));
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMin));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, "asc");
        assertEquals(8, responseGetAllMoviesList.get(0).getPrice(), 0);
    }

    @Test
    public void sortByPriceDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("price", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("price", "8");
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMin));
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, "desc");
        assertEquals(10, responseGetAllMoviesList.get(0).getPrice(), 0);
    }

    @Test
    public void sortByRatingDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("rating", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("rating", "8");
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMin));
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByRating(responseGetAllMoviesList, "desc");
        assertEquals(10, responseGetAllMoviesList.get(0).getRating(), 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sortByRatingAsc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("rating", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("rating", "8");
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMin));
        responseGetAllMoviesList.add(new AllMoviesDTO(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByRating(responseGetAllMoviesList, "asc");
    }
}