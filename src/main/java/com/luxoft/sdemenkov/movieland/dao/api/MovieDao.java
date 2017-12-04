package com.luxoft.sdemenkov.movieland.dao.api;

import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCountPair;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Queue;
import java.util.Set;


public interface MovieDao {
    List<Movie> getAll();

    List<Movie> getThreeRandomMovies();

    int getCountOfMovies();

    List<Movie> getMovieListByIds(Set<Integer> ids);

    List<Movie> getMoviesByGenre(int genreId);

    void save(Movie movie);

    void update(Movie movie);

    List<Movie> searchByTitle(String title);

    List<Movie> searchByTitle(String title, int pageNumber, int moviesPerPage);

    double recalculateRateForMovie(int movieId, RatingToCountPair pair);

    void removeRate(Rate rate);

    double flush(Rate rate);

    Queue<Rate> getBufferedRates();

    int flushAll();
    void saveRateToDb(Rate rate);

    Movie enrichMovieWithActualRates(Movie movie);
    boolean saveRate(Rate rate);
    int deleteUsersRateForMovie(int userId, int movieId);
    RatingToCountPair getRatingToCountPair(int movieId);
}
