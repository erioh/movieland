package com.luxoft.sdemenkov.movieland.web.controller;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllGenres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/1/2017.
 */
@RestController
@RequestMapping("v1/genre")
public class GenreController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    public List<ResponseGetAllGenres> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<ResponseGetAllGenres> responseGetAllGenresList = new ArrayList<>();
        List<Genre> genreList = genreService.getAllGenres();
        log.debug("Method getAllGenres. Filling responseGetAllGenresList with genres");
        for (Genre genre :
                genreList) {
            responseGetAllGenresList.add(new ResponseGetAllGenres(genre));
        }

        log.debug("Method getThreeRandomMovies.  It took {} ms", System.currentTimeMillis() - startTime);
        return responseGetAllGenresList;

    }

    private void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
