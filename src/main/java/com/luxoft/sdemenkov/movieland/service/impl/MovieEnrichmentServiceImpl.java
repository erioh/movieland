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
    private int timeoutSeconds;
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
        long deadLine = timeoutSeconds + System.currentTimeMillis();
        Future<?> enrichWithGenre = threadPool.submit(() -> genreService.enrichMoviesWithGenres(movieList));
        Future<?> enrichWithCountry = threadPool.submit(() -> countryService.enrichMoviesWithCountries(movieList));
        Future<?> enrichWithReview = threadPool.submit(() -> reviewService.enrichMoviesWithReviews(movieList));

        List<Future<?>> enrichServiceFutureList = new ArrayList<>();
        enrichServiceFutureList.add(enrichWithGenre);
        enrichServiceFutureList.add(enrichWithCountry);
        enrichServiceFutureList.add(enrichWithReview);
        for (Future<?> enrichService : enrichServiceFutureList) {
            try {
                long timeToDie = deadLine - System.currentTimeMillis();
                enrichService.get(timeToDie, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.warn("Enrichment  is interrupted");
            } catch (ExecutionException e) {
                logger.error("Enrichment is interrupted with exception {}", e.getMessage());
                logger.error(e.getMessage());
            } catch (TimeoutException e) {
                enrichService.cancel(true);
                logger.warn("Enrichment is interrupted by Timeout");
            }
            logger.debug("Enrichment is finished. ");
        }
    }

}