package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
import com.luxoft.sdemenkov.movieland.web.responce.ResponseGetAllMovies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class SortSortableService implements SortService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public List<Sortable> sortByRating(List<Sortable> list, String expression) {
        log.debug("Start to sortByRating.");
        if(!expression.equals("desc")) {
            log.error("IllegalArgumentException exception was thrown by sortByRating. " +
                    "Invalid value of parameter 'rating' is {}", expression);
            throw new IllegalArgumentException("Only sorting descending is supported. " +
                    "Used incorrect parameter is '" + expression + "'" );
        }
        Collections.sort(list, Comparator.comparingDouble(Sortable::getRating).reversed());
        return list;
    }
}
