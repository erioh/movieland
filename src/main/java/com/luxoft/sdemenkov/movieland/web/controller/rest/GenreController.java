package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.business.Genre;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.web.response.AllGenresDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/genre")
public class GenreController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<?> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<AllGenresDto> allGenresDtoList = new ArrayList<>();
        List<Genre> genreList = genreService.getAll();
        log.debug("Method getAll. Filling allGenresDtoList with genres");
        for (Genre genre : genreList) {
            allGenresDtoList.add(new AllGenresDto(genre));
        }

        log.debug("Method getAll.  It took {} ms", System.currentTimeMillis() - startTime);
        return allGenresDtoList;

    }


    private void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
