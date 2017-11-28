package com.luxoft.sdemenkov.movieland.dao.cache;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
public class CachedMovieDao implements MovieDao {
    private Map<Integer, SoftReference<Movie>> cachedMovieMap = new ConcurrentHashMap<>();

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getMovieListByIds(Set<Integer> ids) {
        logger.debug("Start cached getMovieListByIds. ");
        List<Movie> movieListForReturn = new ArrayList<>();
        for (Integer id : ids) {
            Optional<SoftReference<Movie>> movieSoft = Optional.ofNullable(cachedMovieMap.get(id));
            if (movieSoft.isPresent()) {
                Optional<Movie> movieOptional = Optional.ofNullable(movieSoft.get().get());
                movieListForReturn.add(movieOptional.get());
                logger.debug("Cached getMovieListByIds. movie {} is found in cache", movieOptional);
            } else {
                logger.debug("Cached getMovieListByIds. movie with id ={} is not found in cache", id);
                Set<Integer> nonChachedMovieId = new HashSet<>();
                nonChachedMovieId.add(id);
                List<Movie> movieListByIds = movieDao.getMovieListByIds(nonChachedMovieId);
                movieListForReturn.add(movieListByIds.get(0));
                cachedMovieMap.put(movieListByIds.get(0).getId(), new SoftReference<Movie>(movieListByIds.get(0)));
            }
        }
        return movieListForReturn;
    }

    @Override
    public void save(Movie movie) {
        logger.debug("Cached save. Method is called");
        cachedMovieMap.put(movie.getId(), new SoftReference<Movie>(movie));
        movieDao.save(movie);

    }

    @Override
    public void update(Movie movie) {
        logger.debug("Cached update. Method is called");
        Optional<SoftReference<Movie>> movieToBeChanged = Optional.empty();
        movieToBeChanged = Optional.ofNullable(cachedMovieMap.get(movie.getId()));

        if (movieToBeChanged.isPresent()) {
            logger.debug("Cached update. movie {} is present in cache. ", movie);
            logger.debug("Movie to be cached is {}", movieToBeChanged);
            logger.debug("Stored cache is {}", cachedMovieMap);
            int index = movieToBeChanged.get().get().getId();
            logger.debug("Cached update. index of cached movie is  {} ", index);
            cachedMovieMap.put(index, new SoftReference<Movie>(movie));
            logger.debug("Cached update. movie {} is changed in cache. ", movie);
        } else {
            logger.debug("Cached update. movie {} is NOT present in cache. ", movie);
            cachedMovieMap.put(movie.getId(), new SoftReference<Movie>(movie));
            logger.debug("Cached update. movie {} is putted into cache ", movie);
        }

        movieDao.update(movie);
        logger.debug("Cached update. movie {} is changed in DataBase ", movie);
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        return movieDao.searchByTitle(title);
    }

    @Override
    public List<Movie> searchByTitle(String title, int pageNumber, int moviesPerPage) {
        return movieDao.searchByTitle(title, pageNumber, moviesPerPage);
    }


    @Override
    public List<Movie> getAll() {
        return movieDao.getAll();
    }

    @Override
    public List<Movie> getThreeRandomMovies() {
        return movieDao.getThreeRandomMovies();
    }

    @Override
    public int getCountOfMovies() {
        return movieDao.getCountOfMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(int genreId) {
        return movieDao.getMoviesByGenre(genreId);
    }

}
