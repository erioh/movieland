package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.ReviewDao;
import com.luxoft.sdemenkov.movieland.model.business.Movie;
import com.luxoft.sdemenkov.movieland.model.business.Review;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.testutils.MovieGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
        reviewDao.enrichMoviesWithReviews(movieList);
        Review review = movieList.get(0).getReviewList().get(0);
        assertEquals(1, review.getId());
        assertEquals(2, review.getUser().getId());
    }

    @Test
    public void saveReview() throws Exception {

        Review review = new Review();
        User user = new User();
        user.setEmail("user@u.com");
        user.setId(125);
        user.setNickname("nickname");
        review.setText("Review");
        review.setUser(user);
        int i = reviewDao.saveReview(review, MovieGenerator.getMovieForTest());
        assertEquals(1, i);
    }

}