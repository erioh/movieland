package com.luxoft.sdemenkov.movieland.model.technical;


public enum SortDirection {
    ASC("ASC"), DESC("DESC");
    private final String direction;

    SortDirection(String direction) {
        this.direction = direction;
    }

    public static SortDirection getDirection(String direction) {
        for (SortDirection sortDirection : values()) {
            if (sortDirection.direction.equalsIgnoreCase(direction)) {
                return sortDirection;
            }
        }
        return null;
    }
}
