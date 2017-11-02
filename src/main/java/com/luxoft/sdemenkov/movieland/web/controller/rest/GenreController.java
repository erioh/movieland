package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.AllGenresDTO;
import com.luxoft.sdemenkov.movieland.web.responce.MovieByGenreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/1/2017.
 */
@RestController
@RequestMapping("/genre")
public class GenreController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreService genreService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private SortService sortService;

    @RequestMapping(method = RequestMethod.GET)
    public List<AllGenresDTO> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<AllGenresDTO> allGenresDTOList = new ArrayList<>();
        List<Genre> genreList = genreService.getAllGenres();
        log.debug("Method getAllGenres. Filling allGenresDTOList with genres");
        for (Genre genre :
                genreList) {
            allGenresDTOList.add(new AllGenresDTO(genre));
        }

        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);
        return allGenresDTOList;

    }

    @RequestMapping(value = "/{genreId}", params = { "rating","price" }, method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenreSorted(@PathVariable(value = "genreId") int genreId
            , @RequestParam(value = "rating", defaultValue = "d") String rating
            , @RequestParam(value = "price",  defaultValue = "d") String price) {
        System.out.println(genreId);
        System.out.println(rating);
        System.out.println(price);
        List<Sortable> responseGetMovieByGenre = getMoviesByGenre(genreId);
        long startTime = System.currentTimeMillis();
        if(!"d".equals(rating)) {
            responseGetMovieByGenre = sortService.sortByRating(responseGetMovieByGenre, price);
        }
        if(!"d".equals(price)) {
            responseGetMovieByGenre = sortService.sortByPrice(responseGetMovieByGenre, rating);
        }
        return responseGetMovieByGenre;

    }

        @RequestMapping(value = "/{genreId}", method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenre(@PathVariable(name = "genreId") int genreId) {
        long startTime = System.currentTimeMillis();
        List<Sortable> responseGetMovieByGenreList = new ArrayList<>();
        List<Movie> movieList = movieService.getMoviesByGenre(genreId);
        for (Movie movie : movieList) {
            responseGetMovieByGenreList.add(new MovieByGenreDTO(movie));
        }
        log.debug("Method getThreeRandomMoviesSortedByRating (Sorting part).  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetMovieByGenreList;
    }

    private void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
