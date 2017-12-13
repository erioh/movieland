package com.luxoft.sdemenkov.movieland.dao.jdbc.impl;


import com.luxoft.sdemenkov.movieland.dao.api.RateDao;
import com.luxoft.sdemenkov.movieland.model.business.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Queue;

public class JdbcRateDao implements RateDao {

    @Autowired
    String saveBufferedRatesSql;

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @Override
    public void saveBufferedRates(Queue<Rate> queue) {


    }
}
