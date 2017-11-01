package com.luxoft.sdemenkov.movieland.web.responce;

import com.luxoft.sdemenkov.movieland.model.Genre;

/**
 * Created by sergeydemenkov on 28.10.17.
 */
public class ResponseGetAllGenres {
    private int id;
    private String name;

    public ResponseGetAllGenres() {
    }

    public ResponseGetAllGenres(Genre genre) {
        id = genre.getId();
        name = genre.getName();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ResponseGetAllGenres{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
