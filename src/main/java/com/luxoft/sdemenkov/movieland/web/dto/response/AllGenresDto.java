package com.luxoft.sdemenkov.movieland.web.dto.response;

import com.luxoft.sdemenkov.movieland.model.business.Genre;


public class AllGenresDto {
    private int id;
    private String name;

    public AllGenresDto() {
    }

    public AllGenresDto(Genre genre) {
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
        return "AllGenresDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
