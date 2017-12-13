package com.luxoft.sdemenkov.movieland.dao.api;


import com.luxoft.sdemenkov.movieland.model.business.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Queue;

public interface RateDao {

    void saveBufferedRates(Queue<Rate> queue);
}
