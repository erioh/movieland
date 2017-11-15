package com.luxoft.sdemenkov.movieland.web.response;

import com.luxoft.sdemenkov.movieland.model.Genre;


public class AllGenresDTO {
    private int id;
    private String name;

    public AllGenresDTO() {
    }

    public AllGenresDTO(Genre genre) {
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
        return "AllGenresDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
