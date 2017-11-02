package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
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
    private SortService sortService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Sortable> getAllMovies() {
        long startTime = System.currentTimeMillis();
        List<Sortable> responseGetAllMoviesList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie :
                movieList) {
            responseGetAllMoviesList.add(new ResponseGetAllMovies(movie));
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

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenre(@PathVariable(name = "genreId") int genreId) {
        long startTime = System.currentTimeMillis();
        List<Sortable> responseGetMovieByGenreList = new ArrayList<>();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        for (Movie movie : movieList) {
            responseGetMovieByGenreList.add(new ResponseGetMovieByGenre(movie));
        }
        log.debug("Method getThreeRandomMoviesSortedByRating (Sorting part).  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetMovieByGenreList;
    }

    @RequestMapping(value = "/genre/{genreId}", params = {"rating", "price"}, method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenreSorted(@PathVariable(name = "genreId") int genreId
            , @RequestParam(required = false) String rating
            , @RequestParam(required = false) String price) {
        List<Sortable> responseGetMovieByGenre = getMoviesByGenre(genreId);
        System.out.println("AAAAAAAAAAAA");
        long startTime = System.currentTimeMillis();
        if(rating != null) {
            responseGetMovieByGenre = sortService.sortByPrice(responseGetMovieByGenre, price);
        }
        if(price != null) {
            responseGetMovieByGenre = sortService.sortByRating(responseGetMovieByGenre, rating);
        }
        return responseGetMovieByGenre;

    }

    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

}
