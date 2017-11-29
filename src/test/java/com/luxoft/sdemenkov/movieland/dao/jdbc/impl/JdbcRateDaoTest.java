package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;

import com.luxoft.sdemenkov.movieland.dao.api.RateDao;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.technical.RatingToCounPair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring-test-config.xml")
@Transactional
public class JdbcRateDaoTest {
    @Autowired
    private RateDao rateDao;

    @Test
    public void saveRate() throws Exception {
        Rate rate = new Rate();
        rate.setUserId(2);
        rate.setMovieId(2);
        rate.setRating(3);
        int count = rateDao.saveRate(rate);
        assertEquals(1, count);
    }

    @Test
    public void deleteUsersRateForMovie() throws Exception {
        int userId = 1;
        int movieId = 2;
        int count = rateDao.deleteUsersRateForMovie(userId, movieId);
        assertEquals(1, count);
    }

    @Test
    public void getRatingToCounPair() throws Exception {
        int movieId = 12;
        RatingToCounPair pair = rateDao.getRatingToCountPair(movieId);
        assertEquals(7.9, pair.getRatingSum(), 0);
        assertEquals(1, pair.getCount());
    }

    @Test
    public void recalculateRateForMovie() throws Exception {
        RatingToCounPair pair = new RatingToCounPair(10, 2);
        double result = rateDao.recalculateRateForMovie(1, pair);
        assertEquals(5.0, result, 0);
    }

    @Test
    public void isRatingAdded() throws Exception {
        boolean result = rateDao.isRatingAdded(1, 1);
        assertTrue(result);
        result = rateDao.isRatingAdded(1, 2);
        assertFalse(result);
    }


}