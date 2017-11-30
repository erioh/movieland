package com.luxoft.sdemenkov.movieland.jmx;


public interface CacheInvalidater {
    void invalidateAll();
    void invalidateGenreCache();
    void invalidateMovieCache();
    void invalidateRateCache();
}
