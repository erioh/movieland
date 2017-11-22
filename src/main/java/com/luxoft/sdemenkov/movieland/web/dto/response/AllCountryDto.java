package com.luxoft.sdemenkov.movieland.web.dto.response;

import com.luxoft.sdemenkov.movieland.model.business.Country;

public class AllCountryDto {
    private int id;
    private String name;

    public AllCountryDto(Country genre) {
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
        return "AllCountryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
