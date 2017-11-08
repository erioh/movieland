package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
public class JdbcGenreDaoCached implements GenreDao {
    @Autowired
    private GenreDao jdbcGenreDao;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private List<Genre> inputGenreList = new ArrayList<>();
    private volatile List<Genre> cachedGenreList = new ArrayList<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread;

    });

    public void init() {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
                    inputGenreList = jdbcGenreDao.getAllGenres();
                    cachedGenreList = inputGenreList;
                    logger.debug("Cache for Genre is updated");
                    logger.trace("Cache for Genre is updated");
        }, 0, 4, TimeUnit.HOURS);
    }

    @Override
    public List<Genre> getGenreListByMove(Movie movie) {
        return jdbcGenreDao.getGenreListByMove(movie);
    }

    @Override
    public List<Genre> getAllGenres() {

        return cachedGenreList;

    }

    @Override
    public List<Movie> enrichMoviesWithGenres(List<Movie> movieList) {
        return jdbcGenreDao.enrichMoviesWithGenres(movieList);
    }
}
