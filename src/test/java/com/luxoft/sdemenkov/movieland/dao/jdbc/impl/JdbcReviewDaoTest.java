package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.ReviewDao;
import com.luxoft.sdemenkov.movieland.model.Movie;
import com.luxoft.sdemenkov.movieland.model.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
public class JdbcReviewDaoTest {
    @Autowired
    private
    ReviewDao reviewDao;

    @Test
    public void enrichMoviesWithReviews() throws Exception {
        Movie movie = new Movie();
        movie.setId(1);
        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);
        movieList = reviewDao.enrichMoviesWithReviews(movieList);
        Review review = movieList.get(0).getReviewList().get(0);
        assertEquals(1, review.getId());
        assertEquals(2, review.getUser().getId());


    }

}