package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.SortDirection;
import com.luxoft.sdemenkov.movieland.service.*;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.response.AllMoviesDTO;
import com.luxoft.sdemenkov.movieland.web.response.MovieByIdDTO;
import com.luxoft.sdemenkov.movieland.web.response.MoviesByGenreDTO;
import com.luxoft.sdemenkov.movieland.web.response.ThreeRandomMoviesDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/movie")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private SortService sortService;
    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    private SortDirectionValidationService sortDirectionValidationService;
    @Autowired
    private CurrencyValidationService currencyValidationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAllMovies(
            @RequestParam(value = "rating", required = false) String ratingDirection
            , @RequestParam(value = "price", required = false) String priceDirection) {

        ResponseEntity<String> errors = sortDirectionValidationService.getValidationErrors(ratingDirection, priceDirection);
        if (null != errors){
            return errors;
        }

        long startTime = System.currentTimeMillis();
        List<Sortable> allMoviesDTOList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie : movieList) {
            allMoviesDTOList.add(new AllMoviesDTO(movie));
        }
        if (ratingDirection != null) {
            allMoviesDTOList = sortService.sortByRating(allMoviesDTOList, SortDirection.getDirection(ratingDirection));
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        }
        if (priceDirection != null) {
            allMoviesDTOList = sortService.sortByPrice(allMoviesDTOList, SortDirection.getDirection(priceDirection));
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        }
        log.debug("Method getAllMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return new ResponseEntity<List<Sortable>>(allMoviesDTOList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieById(
            @PathVariable(value = "movieId") int movieId,
            @RequestParam(value = "currency", required = false) String currency) {
        log.debug("Method getMoviesById is called");
        log.debug("Start validation of input paramener");
        ResponseEntity<String> errors = currencyValidationService.getValidationErrors(currency);
        if (errors != null) {
            return errors;
        }
        log.debug("Validation of input parameter is finished");

        long startTime = System.currentTimeMillis();
        Set<Integer> movieIdSet = new HashSet<>();
        movieIdSet.add(movieId);
        List<Movie> movieList = movieService.getMovieById(movieIdSet);
        if (currency != null) {
            movieList = currencyExchangeService.getMovieWithChangedCurrency(movieList, Currency.getCurrency(currency));
        }
        MovieByIdDTO movieByIdDTO = new MovieByIdDTO(movieList.get(0));

        log.debug("Method getMoviesById. It took {} ms", System.currentTimeMillis() - startTime);
        return new ResponseEntity<MovieByIdDTO>(movieByIdDTO, HttpStatus.OK);

    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<?> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ThreeRandomMoviesDTO> threeRandomMovieDTOS = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRandomMovies();
        for (Movie movie : movieList) {
            threeRandomMovieDTOS.add(new ThreeRandomMoviesDTO(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return new ResponseEntity<List<ThreeRandomMoviesDTO>>(threeRandomMovieDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMoviesByGenre(@PathVariable(value = "genreId") int genreId
            , @RequestParam(value = "rating", required = false) String ratingDirection
            , @RequestParam(value = "price", required = false) String priceDirection) {
        ResponseEntity<String> errors = sortDirectionValidationService.getValidationErrors(ratingDirection, priceDirection);
        if (null != errors){
            return errors;
        }
        long startTime = System.currentTimeMillis();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        List<Sortable> movieByGenreDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieByGenreDtoList.add(new MoviesByGenreDTO(movie));
        }
        if (ratingDirection != null) {
            movieByGenreDtoList = sortService.sortByRating(movieByGenreDtoList, SortDirection.getDirection(ratingDirection));
        }
        if (priceDirection != null) {
            movieByGenreDtoList = sortService.sortByPrice(movieByGenreDtoList, SortDirection.getDirection(priceDirection));
        }
        return new ResponseEntity<List<Sortable>>(movieByGenreDtoList, HttpStatus.OK);

    }

    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

}
