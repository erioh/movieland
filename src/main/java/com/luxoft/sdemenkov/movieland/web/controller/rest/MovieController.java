package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllMovies;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetMovieByGenre;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetThreeRandomMovies;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/movie")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private SortService<ResponseGetAllMovies> responseGetAllMoviesSortService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ResponseGetAllMovies> getAllMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponseGetAllMovies> responseGetAllMoviesList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie :
                movieList) {
            responseGetAllMoviesList.add(new ResponseGetAllMovies(movie));
        }
        log.debug("Method getAllMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responseGetAllMoviesList;
    }

    @RequestMapping(params = "rating", method = RequestMethod.GET)
    public List<ResponseGetAllMovies> getAllMoviesSorted( @RequestParam String rating) throws IllegalArgumentException {
        List<ResponseGetAllMovies> responseGetAllMoviesList = getAllMovies();
        responseGetAllMoviesList = responseGetAllMoviesSortService.sort(responseGetAllMoviesList, rating);
        return responseGetAllMoviesList;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public List<ResponseGetThreeRandomMovies> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponseGetThreeRandomMovies> responseGetThreeRandomMovies = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRandomMovies();
        for (Movie movie : movieList) {
            responseGetThreeRandomMovies.add(new ResponseGetThreeRandomMovies(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responseGetThreeRandomMovies;
    }

    @RequestMapping(value = "/genre/{genreId}")
    public List<ResponseGetMovieByGenre> getMoviesByGenre(@PathVariable(name = "genreId") int genreId) {
        long startTime = System.currentTimeMillis();
        List<ResponseGetMovieByGenre> responseGetMovieByGenreList = new ArrayList<>();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        for (Movie movie : movieList) {
            responseGetMovieByGenreList.add(new ResponseGetMovieByGenre(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetMovieByGenreList;
    }


    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
