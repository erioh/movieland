package com.luxoft.sdemenkov.movielend.service;

import com.luxoft.sdemenkov.movielend.dao.jdbc.GenreDao;
import com.luxoft.sdemenkov.movielend.dao.jdbc.impl.GenreDaoImpl;
import com.luxoft.sdemenkov.movielend.model.Genre;
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
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenreDao genreDaoImpl;


    public List<Genre> getAllGenres() {
        List<Genre> genreList = genreDaoImpl.getAllGenres();
        log.debug("Calling method getAllMovies");
        return genreList;

    }


    public GenreDao getGenreDaoImpl() {
        return genreDaoImpl;
    }

    public void setGenreDaoImpl(GenreDao genreDaoImpl) {
        this.genreDaoImpl = genreDaoImpl;
    }

}
