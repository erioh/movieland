package com.luxoft.sdemenkov.movieland.service.impl;

import com.luxoft.sdemenkov.movieland.model.SortDirection;
import com.luxoft.sdemenkov.movieland.service.SortService;
import com.luxoft.sdemenkov.movieland.service.api.Sortable;
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
    public List<Sortable> sortByRating(List<Sortable> list, SortDirection direction) {
        log.debug("Start to sortByRating.");
        Collections.sort(list, Comparator.comparingDouble(Sortable::getRating).reversed());
        return list;
    }

    @Override
    public List<Sortable> sortByPrice(List<Sortable> list, SortDirection direction) {
        log.debug("Start to sortByPrice.");
        switch (direction) {
            case DESC:
                Collections.sort(list, Comparator.comparing(Sortable::getPrice).reversed());
                break;
            case ASC:
                Collections.sort(list, Comparator.comparing(Sortable::getPrice));
                break;
        }

        return list;
    }
}
