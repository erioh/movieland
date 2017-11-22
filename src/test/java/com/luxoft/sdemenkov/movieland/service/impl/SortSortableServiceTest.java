package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.technical.SortDirection;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.dto.response.AllMoviesDto;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;

import java.math.BigDecimal;
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
        responseGetAllMoviesList.add(new AllMoviesDto(movieMax));
        responseGetAllMoviesList.add(new AllMoviesDto(movieMin));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, SortDirection.ASC);
        assertEquals(new BigDecimal(8), responseGetAllMoviesList.get(0).getPrice());
    }

    @Test
    public void sortByPriceDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("price", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("price", "8");
        responseGetAllMoviesList.add(new AllMoviesDto(movieMin));
        responseGetAllMoviesList.add(new AllMoviesDto(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByPrice(responseGetAllMoviesList, SortDirection.DESC);
        assertEquals(new BigDecimal(10), responseGetAllMoviesList.get(0).getPrice());
    }

    @Test
    public void sortByRatingDesc() throws Exception {
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        SortSortableService sortSortableService = new SortSortableService();
        Movie movieMax = MovieGenerator.getMovieForTest("rating", "10");
        Movie movieMin = MovieGenerator.getMovieForTest("rating", "8");
        responseGetAllMoviesList.add(new AllMoviesDto(movieMin));
        responseGetAllMoviesList.add(new AllMoviesDto(movieMax));
        responseGetAllMoviesList = sortSortableService.sortByRating(responseGetAllMoviesList, SortDirection.DESC);
        assertEquals(10, responseGetAllMoviesList.get(0).getRating(), 0);
    }

}