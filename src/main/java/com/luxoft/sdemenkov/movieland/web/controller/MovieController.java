package com.luxoft.sdemenkov.movieland.web.controller;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllGenres;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllMovies;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetThreeRandomMovies;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieService;
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
@RequestMapping("/v1/movie")
public class MovieController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
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

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponseGetThreeRandomMovies> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponseGetThreeRandomMovies> responseGetThreeRandomMovies = new ArrayList<>();
        List<Movie> movieList = movieService.getThreeRandomMovies();
        for (Movie movie :
                movieList) {
            responseGetThreeRandomMovies.add(new ResponseGetThreeRandomMovies(movie));
        }
        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);

        return responseGetThreeRandomMovies;
    }


    private void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }
}
