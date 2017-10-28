package com.luxoft.sdemenkov.movielend.services;

import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDao;
import com.luxoft.sdemenkov.movielend.models.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sergeydemenkov on 28.10.17.
 */

@Service
public class GenreService {
    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenreDao genreDao;


    public List<Genre> getAllGenres() {
        List<Genre> genreList = genreDao.getAllGenres();
        log.info("Calling method getAllMovies, result = {}", genreList);
        return genreList;

    }


    public GenreDao getGenreDao() {
        return genreDao;
    }

    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

}
