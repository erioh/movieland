package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllMovies;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SortResponseGetAllMoviesServiceTest {
    @Test
    public void sort() throws Exception {
        List<ResponseGetAllMovies> responseGetAllMoviesList = new ArrayList<>();
        SortResponseGetAllMoviesService sortResponseGetAllMoviesService = new SortResponseGetAllMoviesService();
        Movie movieMax = new Movie();
        Movie movieMin = new Movie();
        movieMax.setRating(10);
        movieMin.setRating(8);
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMin));
        responseGetAllMoviesList.add(new ResponseGetAllMovies(movieMax));
        responseGetAllMoviesList = sortResponseGetAllMoviesService.sort(responseGetAllMoviesList, "desc");
        assertEquals(10, responseGetAllMoviesList.get(0).getRating(), 0);
    }

}