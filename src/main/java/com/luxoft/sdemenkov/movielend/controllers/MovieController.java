package com.luxoft.sdemenkov.movielend.controllers;

import com.luxoft.sdemenkov.movielend.models.Genre;
import com.luxoft.sdemenkov.movielend.models.Movie;
import com.luxoft.sdemenkov.movielend.models.responces.ResponceGetAllGenres;
import com.luxoft.sdemenkov.movielend.models.responces.ResponceGetAllMovies;
import com.luxoft.sdemenkov.movielend.models.responces.ResponceGetThreeRandomMovies;
import com.luxoft.sdemenkov.movielend.services.GenreService;
import com.luxoft.sdemenkov.movielend.services.MovieService;
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
        List<ResponceGetAllMovies> responceGetAllMoviesList = new ArrayList<ResponceGetAllMovies>();
        List<Movie> movieList = movieService.getAllMovies();
        log.info("Method getAllMovies. Filling responceGetAllMoviesList with movies {}", movieList);
        for (Movie movie :
                movieList) {
            responceGetAllMoviesList.add(new ResponceGetAllMovies(movie));
        }
        log.info("Method getAllMovies. Calling  getAllMovies with {}. It took {} ms", responceGetAllMoviesList, System.currentTimeMillis() - startTime);

        return responceGetAllMoviesList;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponceGetThreeRandomMovies> getThreeRandomMovies() {
        long startTime = System.currentTimeMillis();
        List<ResponceGetThreeRandomMovies> responceGetThreeRandomMovies = new ArrayList<ResponceGetThreeRandomMovies>();
        List<Movie> movieList = movieService.getThreeRundomMovies();
        log.info("Method getThreeRandomMovies. Filling responceGetThreeRandomMovies with movies", movieList);
        for (Movie movie :
                movieList) {
            responceGetThreeRandomMovies.add(new ResponceGetThreeRandomMovies(movie));
        }
        log.info("Method getThreeRandomMovies. Calling  getThreeRandomMovies with {}. It took {} ms", responceGetThreeRandomMovies, System.currentTimeMillis() - startTime);

        return responceGetThreeRandomMovies;
    }

    @RequestMapping(value = "/genre", method = RequestMethod.GET)
    @ResponseBody
    public List<ResponceGetAllGenres> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<ResponceGetAllGenres> responceGetAllGenresList = new ArrayList<>();
        List<Genre> genreList = genreService.getAllGenres();
        log.info("Method getAllGenres. Filling responceGetAllGenresList with genres");
        for (Genre genre :
                genreList) {
            responceGetAllGenresList.add(new ResponceGetAllGenres(genre));
        }

        log.info("Method getThreeRandomMovies. Calling  getThreeRandomMovies with {}. It took {} ms", responceGetAllGenresList, System.currentTimeMillis() - startTime);
        return responceGetAllGenresList;

    }

    public MovieService getMovieService() {
        return movieService;
    }

    public void setMovieService(MovieService movieService) {
        this.movieService = movieService;
    }

    public GenreService getGenreService() {
        return genreService;
    }

    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
