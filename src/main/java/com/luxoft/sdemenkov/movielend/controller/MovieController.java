package com.luxoft.sdemenkov.movielend.controller;

import com.luxoft.sdemenkov.movielend.model.Genre;
import com.luxoft.sdemenkov.movielend.model.Movie;
import com.luxoft.sdemenkov.movielend.model.responce.ResponceGetAllGenres;
import com.luxoft.sdemenkov.movielend.model.responce.ResponceGetAllMovies;
import com.luxoft.sdemenkov.movielend.model.responce.ResponceGetThreeRandomMovies;
import com.luxoft.sdemenkov.movielend.service.GenreService;
import com.luxoft.sdemenkov.movielend.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/v1")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreService genreService;

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponceGetAllMovies> getAllMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponceGetAllMovies> responceGetAllMoviesList = new ArrayList<>();
        List<Movie> movieList = movieService.getAllMovies();
        for (Movie movie :
                movieList) {
            responceGetAllMoviesList.add(new ResponceGetAllMovies(movie));
        }
        log.debug("Method getAllMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responceGetAllMoviesList;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponceGetThreeRandomMovies> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponceGetThreeRandomMovies> responceGetThreeRandomMovies = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRundomMovies();
        for (Movie movie :
                movieList) {
            responceGetThreeRandomMovies.add(new ResponceGetThreeRandomMovies(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responceGetThreeRandomMovies;
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponceGetAllGenres> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<ResponceGetAllGenres> responceGetAllGenresList = new ArrayList<>();
        List<Genre> genreList = genreService.getAllGenres();
        log.debug("Method getAllGenres. Filling responceGetAllGenresList with genres");
        for (Genre genre :
                genreList) {
            responceGetAllGenresList.add(new ResponceGetAllGenres(genre));
        }

        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);
        return responceGetAllGenresList;

    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
