package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
@EnableScheduling
@PropertySource("classpath:property/cron.properties")
public class CachedGenreDao implements GenreDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier(value = "jdbcGenreDao")
    private GenreDao genreDao;

    private volatile List<Genre> cachedGenreList = new ArrayList<>();

    @PostConstruct
    @Scheduled(fixedRateString = "${cron.cache.genre.schedule}", initialDelayString = "${cron.cache.genre.schedule}")
    public void invalidate() {
            cachedGenreList = genreDao.getAllGenres();
            logger.debug("Cache for Genre is updated");
            logger.trace("Cache for Genre is updated. Cahce value is {}", cachedGenreList);
    }

    @Override
    public List<Genre> getGenreListByMove(Movie movie) {
        return genreDao.getGenreListByMove(movie);
    }

    @Override
    public List<Genre> getAllGenres() {
        return cachedGenreList;

    }

    @Override
    public void enrichMoviesWithGenres(List<Movie> movieList) {
        genreDao.enrichMoviesWithGenres(movieList);
    }
}
