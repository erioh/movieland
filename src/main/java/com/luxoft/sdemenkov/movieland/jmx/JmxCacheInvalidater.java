package com.luxoft.sdemenkov.movieland.jmx;


import com.luxoft.sdemenkov.movieland.dao.api.GenreDao;
import com.luxoft.sdemenkov.movieland.dao.api.MovieDao;
import com.luxoft.sdemenkov.movieland.dao.cache.CachedGenreDao;
import com.luxoft.sdemenkov.movieland.dao.cache.CachedMovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

@Service
@ManagedResource(objectName = "MovieLandCache:name=Invalidate")
public class JmxCacheInvalidater implements CacheInvalidater {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreDao genreDao;
    @Autowired
    private MovieDao movieDao;


    @Override
    @ManagedOperation
    public void invalidateAll() {
        invalidateGenreCache();
        invalidateMovieCache();
        invalidateRateCache();
    }

    @Override
    @ManagedOperation
    public void invalidateGenreCache() {
        logger.info("Jmx. Genre Cache invalidating");
        if (genreDao instanceof CachedGenreDao) {
            ((CachedGenreDao) genreDao).invalidate();
        } else {
            logger.info("Genre Dao Is not Cached");
        }
    }

    @ManagedOperation
    public void invalidateMovieCache() {
        logger.info("Jmx. Movie Cache invalidating");
        if(movieDao instanceof CachedMovieDao) {
            ((CachedMovieDao) movieDao).invalidate();
        } else {
            logger.info("Movie Dao Is not Cached");
        }
    }

    @ManagedOperation
    public void invalidateRateCache() {
        // Rating is not cached yet.
    }
}
