package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.Currency;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.model.technical.Pair;
import com.luxoft.sdemenkov.movieland.model.technical.SortDirection;
import com.luxoft.sdemenkov.movieland.model.technical.Token;
import com.luxoft.sdemenkov.movieland.security.Protected;
import com.luxoft.sdemenkov.movieland.security.TokenPrincipal;
import com.luxoft.sdemenkov.movieland.security.role.Role;
import com.luxoft.sdemenkov.movieland.service.*;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.dto.request.RateDto;
import com.luxoft.sdemenkov.movieland.web.dto.request.SaveMovieDto;
import com.luxoft.sdemenkov.movieland.web.dto.response.*;
import com.luxoft.sdemenkov.movieland.web.util.MovieBuilder;
import com.luxoft.sdemenkov.movieland.web.util.RateBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @Autowired
    private RateService rateService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getAllMovies(
            @RequestParam(value = "rating", required = false) String ratingDirection
            , @RequestParam(value = "price", required = false) String priceDirection) {

        Pair<SortDirection, SortDirection> sortParameters;
        try {
            sortParameters = sortDirectionValidationService.getValidationErrors(ratingDirection, priceDirection);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        long startTime = System.currentTimeMillis();
        List<Sortable> allMoviesDTOList = new ArrayList<>();
        List<Movie> movieList = movieService.getAll();
        for (Movie movie : movieList) {
            allMoviesDTOList.add(new AllMoviesDto(movie));
        }
        if (ratingDirection != null) {
            allMoviesDTOList = sortService.sortByRating(allMoviesDTOList, sortParameters.getFirstValue());
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        }
        if (priceDirection != null) {
            allMoviesDTOList = sortService.sortByPrice(allMoviesDTOList, sortParameters.getSecondValue());
            log.debug("Sorting.  It took {} ms", System.currentTimeMillis() - startTime);
        }
        log.debug("Method getAll.  It took {} ms", System.currentTimeMillis() - startTime);

        return new ResponseEntity<>(allMoviesDTOList, HttpStatus.OK);
    }

    @Protected(protectedBy = Role.ADMIN)
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> saveMovie(@RequestBody SaveMovieDto saveMovieDto) {
        log.debug("saveMovie. Method is called. Changes = {}", saveMovieDto);
        log.info("method saveMovie is called");

        Movie movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getCountries().getGenres().build();
        movieService.save(movie);

        return new ResponseEntity<>(new SaveMovieDto(movie), HttpStatus.OK);
    }

    @RequestMapping(value = "/{movieId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMovieById(
            @PathVariable(value = "movieId") int movieId,
            @RequestParam(value = "currency", required = false) String currency) {
        log.info("Method getMoviesById is called");
        log.debug("Start validation of input parameter");
        log.debug("Validation of input parameter is finished");

        long startTime = System.currentTimeMillis();
        Set<Integer> movieIdSet = new HashSet<>();
        movieIdSet.add(movieId);
        List<Movie> movieList = movieService.getMovieById(movieIdSet);
        if (currency != null) {
            movieList = currencyExchangeService.getMovieWithChangedCurrency(movieList, Currency.getCurrency(currency));
        }
        MovieByIdDto movieByIdDto = new MovieByIdDto(movieList.get(0));

        log.debug("Method getMoviesById. It took {} ms", System.currentTimeMillis() - startTime);
        return new ResponseEntity<>(movieByIdDto, HttpStatus.OK);

    }

    @Protected(protectedBy = Role.ADMIN)
    @RequestMapping(value = "/{movieId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> updateMovie(@RequestBody SaveMovieDto saveMovieDto) {
        log.debug("setMovie. Method is called. Changes = {}", saveMovieDto);
        log.info("method setMovie is called");
        Movie movie = MovieBuilder.fromMovieDto(saveMovieDto).getMovie().getCountries().getGenres().build();
        movieService.update(movie);
        return new ResponseEntity<>(new SaveMovieDto(movie), HttpStatus.OK);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    public ResponseEntity<?> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ThreeRandomMoviesDto> threeRandomMovieDTOS = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRandomMovies();
        for (Movie movie : movieList) {
            threeRandomMovieDTOS.add(new ThreeRandomMoviesDto(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return new ResponseEntity<>(threeRandomMovieDTOS, HttpStatus.OK);
    }

    @RequestMapping(value = "/genre/{genreId}", method = RequestMethod.GET)
    public ResponseEntity<?> getMoviesByGenre(@PathVariable(value = "genreId") int genreId
            , @RequestParam(value = "rating", required = false) String ratingDirection
            , @RequestParam(value = "price", required = false) String priceDirection) {
        Pair<SortDirection, SortDirection> sortParameters;
        try {
            sortParameters = sortDirectionValidationService.getValidationErrors(ratingDirection, priceDirection);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
        long startTime = System.currentTimeMillis();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        List<Sortable> movieByGenreDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieByGenreDtoList.add(new MoviesByGenreDto(movie));
        }
        if (ratingDirection != null) {
            movieByGenreDtoList = sortService.sortByRating(movieByGenreDtoList, sortParameters.getFirstValue());
        }
        if (priceDirection != null) {
            movieByGenreDtoList = sortService.sortByPrice(movieByGenreDtoList, sortParameters.getSecondValue());
        }
        log.debug("getMoviesByGenre. It took {} ms", System.currentTimeMillis() - startTime);
        return new ResponseEntity<>(movieByGenreDtoList, HttpStatus.OK);

    }

    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> searchMovie(@RequestParam(value = "title") String title,
                                         @RequestParam(value = "page", required = false) Integer pageNumber) {
        log.info("Starting search movie by title {}", title);
        List<Movie> movieList;
        if (null == pageNumber || 0 == pageNumber) {
            movieList = movieService.searchByTitle(title);
            log.debug("searchMovie. Results of searching with title = {} is {}", title, movieList);
        } else {
            try {
                movieList = movieService.searchByTitle(title, pageNumber);
            } catch (RuntimeException e) {
                return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
            }
            log.debug("searchMovie. Results of searching with title = {} is {} for page number {}", title, movieList, pageNumber);
        }
        List<SearchForMoviesDto> searchForMoviesDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            searchForMoviesDtoList.add(new SearchForMoviesDto(movie));
        }
        return new ResponseEntity<>(searchForMoviesDtoList, HttpStatus.OK);
    }

    @Protected(protectedBy = Role.USER)
    @RequestMapping(value = "/{movieId}/rate", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void rateMovie(@PathVariable(value = "movieId") Integer movieId, @RequestBody RateDto rateDto, TokenPrincipal principal) {
        log.info("rateMovie is called");
        log.debug("rateMovie is called for movieId = {} and rateDto = {}", movieId, rateDto);
        Token token = principal.getToken().get();
        User user = token.getUser();
        Rate rate = RateBuilder.from(rateDto).withMovieId(movieId).withUser(user).build();
        rateService.saveRate(rate);

    }

    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setSortService(SortService sortService) {
        this.sortService = sortService;
    }

}
