package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.service.CountryService;
import com.luxoft.sdemenkov.movieland.service.GenreService;
import com.luxoft.sdemenkov.movieland.service.MovieEnrichmentService;
import com.luxoft.sdemenkov.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class MovieEnrichmentServiceImpl implements MovieEnrichmentService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${cron.movie.enrich.timeout.milliseconds}")
    private int timeoutMilliseconds;
    @Autowired
    private GenreService genreService;
    @Autowired
    private CountryService countryService;
    @Autowired
    private ReviewService reviewService;

    private ExecutorService threadPool = Executors.newCachedThreadPool();

    @Override
    public void enrich(List<Movie> movieList) {
        logger.info("Parallel Enrichment is started");
        long deadLine = timeoutMilliseconds + System.currentTimeMillis();
        Future<?> enrichWithGenreFuture = threadPool.submit(() -> genreService.enrichMoviesWithGenres(movieList));
        Future<?> enrichWithCountryFuture = threadPool.submit(() -> countryService.enrichMoviesWithCountries(movieList));
        Future<?> enrichWithReviewFuture = threadPool.submit(() -> reviewService.enrichMoviesWithReviews(movieList));

        List<Future<?>> enrichServiceFutureList = new ArrayList<>();
        enrichServiceFutureList.add(enrichWithGenreFuture);
        enrichServiceFutureList.add(enrichWithCountryFuture);
        enrichServiceFutureList.add(enrichWithReviewFuture);
        for (Future<?> enrichServiceFutue : enrichServiceFutureList) {
            try {
                long timeToDie;
                if(( timeToDie= deadLine - System.currentTimeMillis()) < 0) {
                    timeToDie = 0;
                }
                enrichServiceFutue.get(timeToDie, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.warn("Enrichment  is interrupted by somebody else");
            } catch (ExecutionException e) {
                logger.error("Enrichment is interrupted with exception {}", e);
            } catch (TimeoutException e) {
                enrichServiceFutue.cancel(true);
                logger.warn("Enrichment is interrupted by Timeout");
            }

        }
        logger.debug("Enrichment is finished. ");
    }

}