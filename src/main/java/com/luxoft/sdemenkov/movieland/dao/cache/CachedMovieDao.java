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

@Repository
@Primary
public class CachedMovieDao implements MovieDao {
    private volatile List<SoftReference<Movie>> cachedMovieList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getMovieListByIds(Set<Integer> ids) {
        logger.debug("Start cached getMovieListByIds. ");

        List<Movie> movieListForReturn = new ArrayList<>();
        for (Integer id : ids) {
            boolean isMovieCached = false;
            for (SoftReference<Movie> movie : cachedMovieList) {
                if (movie.get().getId() == id) {
                    movieListForReturn.add(movie.get());
                    isMovieCached = true;

                    logger.debug("Cached getMovieListByIds. movie {} is found in cache", movie);
                }
            }
            if (!isMovieCached) {
                logger.debug("Cached getMovieListByIds. movie with id ={} is not found in cache", id);

                Set<Integer> notChachedMovieId = new HashSet<>();
                notChachedMovieId.add(id);
                List<Movie> movieListByIds = movieDao.getMovieListByIds(notChachedMovieId);
                movieListForReturn.add(movieListByIds.get(0));
                cachedMovieList.add(new SoftReference<Movie>(movieListByIds.get(0)));
            }
        }
        return movieListForReturn;
    }

    @Override
    public void save(Movie movie) {
        logger.debug("Cached save. Method is called");
        cachedMovieList.add(new SoftReference<Movie>(movie));
        movieDao.save(movie);

    }

    @Override
    public void set(Movie movie) {
        logger.debug("Cached set. Method is called");
        Optional<SoftReference<Movie>> movieToBeChanged = Optional.empty();

        for (SoftReference<Movie> cachedMovie : cachedMovieList) {
            if (cachedMovie.get().getId() == movie.getId()) {
                movieToBeChanged = Optional.of(cachedMovie);
            }
        }

        if (movieToBeChanged.isPresent()) {
            logger.debug("Cached set. movie {} is present in cache. ", movie);
            logger.debug("Movie to be cached is {}", movieToBeChanged);
            logger.debug("Stored cache is {}", cachedMovieList);

            int index = cachedMovieList.indexOf(movieToBeChanged.get());

            logger.debug("Cached set. index of cached movie is  {} ", index);
            cachedMovieList.set(index, new SoftReference<Movie>(movie));
            logger.debug("Cached set. movie {} is changed in cache. ", movie);
        } else {
            logger.debug("Cached set. movie {} is NOT present in cache. ", movie);

            cachedMovieList.add(new SoftReference<Movie>(movie));

            logger.debug("Cached set. movie {} is putted into cache ", movie);
        }

        movieDao.set(movie);
        logger.debug("Cached set. movie {} is changed in DataBase ", movie);
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
