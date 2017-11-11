package com.luxoft.sdemenkov.movieland.model;

import java.security.InvalidParameterException;

/**
 * Created by sergeydemenkov on 08.11.17.
 */
public enum SortDirection {
    ASC("ASC"), DESC("DESC");
    private final String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public static SortDirection getDirection(String direction) {
        for (SortDirection sortDirection : values()) {
            if(sortDirection.direction.equalsIgnoreCase(direction)) {
                return sortDirection;
            }
        }
        return null;
    }
}
