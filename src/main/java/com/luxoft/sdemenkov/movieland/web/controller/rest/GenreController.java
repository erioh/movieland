package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.web.response.AllGenresDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(method = RequestMethod.GET)
    public List<?> getAllGenres() {
        long startTime = System.currentTimeMillis();
        List<AllGenresDTO> allGenresDTOList = new ArrayList<>();
        List<Genre> genreList = genreService.getAllGenres();
        log.debug("Method getAllGenres. Filling allGenresDTOList with genres");
        for (Genre genre : genreList) {
            allGenresDTOList.add(new AllGenresDTO(genre));
        }

        log.debug("Method getAllGenres.  It took {} ms", System.currentTimeMillis() - startTime);
        return allGenresDTOList;

    }


    private void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }
}
