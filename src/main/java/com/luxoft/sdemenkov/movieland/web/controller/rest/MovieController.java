package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.AllMoviesDTO;
import com.luxoft.sdemenkov.movieland.web.responce.MovieByGenreDTO;
import com.luxoft.sdemenkov.movieland.web.responce.ThreeRandomMoviesDTO;
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
    private SortService sortService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Sortable> getAllMovies() {
        long startTime = System.currentTimeMillis();
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie :
                movieList) {
            responseGetAllMoviesList.add(new AllMoviesDTO(movie));
        }
        log.debug("Method getAllMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responseGetAllMoviesList;
    }

    @RequestMapping(params = "rating", method = RequestMethod.GET)
    public List<Sortable> getAllMoviesSortedByRating(@RequestParam String rating)   {
        List<Sortable> responseGetAllMoviesList = getAllMovies();
        long startTime = System.currentTimeMillis();
        responseGetAllMoviesList =  sortService.sortByRating(responseGetAllMoviesList, rating);
        log.debug("Method getAllMoviesSortedByRating (Sorting).  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetAllMoviesList;
    }
    @RequestMapping(params = "price", method = RequestMethod.GET)
    public List<Sortable> getAllMoviesSortedByPrice(@RequestParam String price)   {
        List<Sortable> responseGetAllMoviesList = getAllMovies();
        long startTime = System.currentTimeMillis();
        responseGetAllMoviesList =  sortService.sortByPrice(responseGetAllMoviesList, price);
        log.debug("Method getAllMoviesSortedByPrice (Sorting).  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetAllMoviesList;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public List<ThreeRandomMoviesDTO> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ThreeRandomMoviesDTO> threeRandomMovieDTOS = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRandomMovies();
        for (Movie movie : movieList) {
            threeRandomMovieDTOS.add(new ThreeRandomMoviesDTO(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return threeRandomMovieDTOS;
    }

    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

}
