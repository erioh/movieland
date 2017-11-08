package com.luxoft.sdemenkov.movieland.web.responce;

import com.luxoft.sdemenkov.movieland.model.Country;

public class AllCountryDTO {
    private int id;
    private String name;

    public AllCountryDTO(Country genre) {
        id = genre.getId();
        name = genre.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AllCountryDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
