package com.luxoft.sdemenkov.movieland.web.dto;

import com.luxoft.sdemenkov.movieland.model.business.Country;

public class CountryDto {
    private int id;
    private String name;

    public CountryDto(Country country) {
        this.id = country.getId();
        this.name = country.getName();
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
        return "CountryDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
