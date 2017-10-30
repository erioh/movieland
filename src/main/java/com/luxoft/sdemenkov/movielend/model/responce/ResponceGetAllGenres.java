package com.luxoft.sdemenkov.movielend.model.responce;

import com.luxoft.sdemenkov.movielend.model.Genre;

/**
 * Created by sergeydemenkov on 28.10.17.
 */
public class ResponceGetAllGenres {
    private int id;
    private String name;

    public ResponceGetAllGenres() {
    }

    public ResponceGetAllGenres(Genre genre) {
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
        return "ResponceGetAllGenres{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
