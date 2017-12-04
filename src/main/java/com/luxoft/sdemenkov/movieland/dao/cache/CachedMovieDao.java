package com.luxoft.sdemenkov.movieland.dao.cache;

import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCountPair;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.lang.ref.SoftReference;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary
@ManagedResource(objectName = "MovieLandCache:name=MovieCacheInvalidator")
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
            try {
                if (movieSoft.isPresent()) {
                    Optional<Movie> movieOptional = Optional.ofNullable(movieSoft.get().get());
                    if (movieOptional.isPresent()) {
                        movieListForReturn.add(movieOptional.get());
                        logger.debug("Cached getMovieListByIds. movie {} is found in cache", movieOptional);
                    } else {
                        throw new NullPointerException("cache is invalidated");
                    }
                } else {
                    throw new NullPointerException("cache is invalidated");
                }
            } catch (NullPointerException e) {
                logger.debug("Cached getMovieListByIds. movie with id ={} is not found in cache", id);
                Set<Integer> nonChachedMovieId = new HashSet<>();
                nonChachedMovieId.add(id);
                List<Movie> movieListByIds = movieDao.getMovieListByIds(nonChachedMovieId);
                movieListForReturn.add(movieListByIds.get(0));
                cachedMovieMap.put(movieListByIds.get(0).getId(), new SoftReference<>(movieListByIds.get(0)));
            }
        }
        return movieListForReturn;
    }

    @Override
    public void save(Movie movie) {
        logger.debug("Cached save. Method is called");
        cachedMovieMap.put(movie.getId(), new SoftReference<>(movie));
        movieDao.save(movie);

    }

    @Override
    public void update(Movie movie) {
        logger.debug("Cached update. Method is called");
        Optional<SoftReference<Movie>> movieToBeChanged;
        movieToBeChanged = Optional.ofNullable(cachedMovieMap.get(movie.getId()));

        try {
            if (movieToBeChanged.isPresent()) {
                Optional<Movie> movieOptional = Optional.ofNullable(movieToBeChanged.get().get());
                if (movieOptional.isPresent()) {
                    int index = movieOptional.get().getId();
                    cachedMovieMap.put(index, new SoftReference<>(movie));
                    logger.debug("Cached update. movie {} is present in cache. ", movie);
                    logger.debug("Movie to be cached is {}", movieToBeChanged);
                    logger.debug("Stored cache is {}", cachedMovieMap);
                    logger.debug("Cached update. index of cached movie is  {} ", index);
                    logger.debug("Cached update. movie {} is changed in cache. ", movie);
                } else {
                    throw new NullPointerException("cache is invalidated");
                }
            } else {
                throw new NullPointerException("cache is invalidated");
            }
        } catch (NullPointerException e) {
            logger.debug("Cached update. movie {} is NOT present in cache. ", movie);
            cachedMovieMap.put(movie.getId(), new SoftReference<>(movie));
            logger.debug("Cached update. movie {} is putted into cache ", movie);
        }

        movieDao.update(movie);
        logger.debug("Cached update. movie {} is changed in DataBase ", movie);
    }

    @Override
    public double recalculateRateForMovie(int movieId, RatingToCountPair pair) {
        return movieDao.recalculateRateForMovie(movieId, pair);
    }

    @Override
    @Scheduled(fixedDelayString = "${cron.dao.rates.buffered.flush}", initialDelayString = "${cron.dao.rates.buffered.flush}")
    public int flushAll() {
        int count = 0;
        Queue<Rate> rateQueue = getBufferedRates();
        for (Rate rate : rateQueue) {
            count++;
            try {
                double newRate = flush(rate);
                SoftReference<Movie> movieSoftReference = cachedMovieMap.get(rate.getMovieId());
                Movie movie = movieSoftReference.get();
                movie.setRating(newRate);
            } catch (NullPointerException e) {
                logger.warn("movie with id = {} is removed from cache", rate.getMovieId());
            } catch (WrongMovieIdException e) {
                logger.warn("Movie with id = {} is not present in DB. Rating is not added", rate.getMovieId());
            }finally {
                removeRate(rate);
            }
        }
        return count;
    }

    @Override
    public Queue<Rate> getBufferedRates() {
        return movieDao.getBufferedRates();
    }

    @Override
    public void removeRate(Rate rate) {
        logger.debug("Rate {} is removed from buffer", rate);
        movieDao.removeRate(rate);
    }

    @Override
    public double flush(Rate rate) {
        return movieDao.flush(rate);
    }

    @Override
    public Movie enrichMovieWithActualRates(Movie movie) {
        return movieDao.enrichMovieWithActualRates(movie);
    }

    @Override
    public boolean saveRate(Rate rate) {
        return movieDao.saveRate(rate);
    }

    @Override
    public int deleteUsersRateForMovie(int userId, int movieId) {
        return movieDao.deleteUsersRateForMovie(userId, movieId);
    }

    @Override
    public RatingToCountPair getRatingToCountPair(int movieId) {
        return movieDao.getRatingToCountPair(movieId);
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

    @Override
    public void saveRateToDb(Rate rate) {
        movieDao.saveRateToDb(rate);
    }

    @ManagedOperation
    public void invalidate() {
        logger.info("Movie Cache is invalidated");
        cachedMovieMap.clear();
    }
}
