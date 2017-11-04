package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.model.Genre;
import com.luxoft.sdemenkov.movieland.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Repository
public class JdbcGenreDaoCached implements GenreDao{
    @Autowired
    GenreDao jdbcGenreDao;

    private List<Genre> inputGenreList = new ArrayList<>();
    private volatile List<Genre> cachedGenreList = new ArrayList<>();
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;

        }
    });

    public JdbcGenreDaoCached() {
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        inputGenreList = jdbcGenreDao.getAllGenres();
                        cachedGenreList = inputGenreList;
                        Thread.sleep(4*3600*1000); // wait for 4 hours before cache reload
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 4, TimeUnit.HOURS);
    }

    @Override
    public List<Genre> getGenreListByMove(Movie movie) {
        return jdbcGenreDao.getGenreListByMove(movie);
    }

    @Override
    public List<Genre> getAllGenres() {
        if(cachedGenreList.size()==0) {
            return jdbcGenreDao.getAllGenres();
        }
            return cachedGenreList;

    }

    @Override
    public List<Movie> enrichMoviesWithGenres(List<Movie> movieList) {
        return jdbcGenreDao.enrichMoviesWithGenres(movieList);
    }
}
