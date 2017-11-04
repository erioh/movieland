package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Currency;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.CurrencyExchangeService;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.AllMoviesDTO;
import com.luxoft.sdemenkov.movieland.web.responce.MovieByIdDTO;
import com.luxoft.sdemenkov.movieland.web.responce.MoviesByGenreDTO;
import com.luxoft.sdemenkov.movieland.web.responce.ThreeRandomMoviesDTO;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<Sortable> getAllMovies(
            @RequestParam(value = "rating", required = false) String rating
            , @RequestParam(value = "price", required = false) String price) {
        if (rating != null && price != null) {
            //            how the fuck I can test it out??
            throw new IllegalArgumentException("You can't sort response by rating and price at the same time");
        }
        long startTime = System.currentTimeMillis();
        List<Sortable> allMoviesDTOList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie :
                movieList) {
            allMoviesDTOList.add(new AllMoviesDTO(movie));
        }
        if(rating != null) {
            allMoviesDTOList =  sortService.sortByRating(allMoviesDTOList, rating);
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        } else if (price != null) {
            allMoviesDTOList =  sortService.sortByPrice(allMoviesDTOList, price);
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        }
        log.debug("Method getAllMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return allMoviesDTOList;
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public MovieByIdDTO getMovieById(
            @PathVariable(value = "movieId") int movieId,
            @RequestParam(value = "currency", required = false) String currency) {
        log.debug("Method getMoviesById is called");
        long startTime = System.currentTimeMillis();
        Set<Integer> movieIdSet = new HashSet<>();
        movieIdSet.add(movieId);
        List<Movie> movieList = movieService.getMovieById(movieIdSet);
        if(currency != null) {
            try {
                movieList = currencyExchangeService.getMovieWithChangedCurrency(movieList, Currency.valueOf(currency));
            } catch (IllegalArgumentException e) {
                log.error("Wrong curency was used! CCY = {}", currency);
                throw e;
            }
        }
        MovieByIdDTO movieByIdDTO = new MovieByIdDTO(movieList.get(0));

        log.debug("Method getMoviesById. It took {} ms", System.currentTimeMillis()-startTime);
        return movieByIdDTO;

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

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenre(@PathVariable(value = "genreId") int genreId
            , @RequestParam(value = "rating",  required = false) String rating
            , @RequestParam(value = "price",   required = false) String price) {
        if (rating != null && price != null) {
//            how the fuck I can test it out??
            throw new IllegalArgumentException("You can't sort response by rating and price at the same time");
        }
        long startTime = System.currentTimeMillis();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        List<Sortable> movieByGenreDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieByGenreDtoList.add(new MoviesByGenreDTO(movie));
        }
        if(rating != null) {
            movieByGenreDtoList = sortService.sortByRating(movieByGenreDtoList, rating);
        } else
        if(price != null) {
            movieByGenreDtoList = sortService.sortByPrice(movieByGenreDtoList, price);
        }
        return movieByGenreDtoList;

    }

    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

}
