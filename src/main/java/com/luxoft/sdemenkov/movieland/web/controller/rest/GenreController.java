package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.AllGenresDTO;
import com.luxoft.sdemenkov.movieland.web.responce.MoviesByGenreDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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

    @RequestMapping(value = "/{genreId}", method = RequestMethod.GET)
    public List<Sortable> getMoviesByGenre(@PathVariable(value = "genreId") int genreId
            , @RequestParam(value = "rating",  required = false) String rating
            , @RequestParam(value = "price",   required = false) String price) {
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


    private void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
