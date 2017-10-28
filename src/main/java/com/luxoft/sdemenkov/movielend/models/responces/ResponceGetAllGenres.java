package com.luxoft.sdemenkov.movielend.models.responces;

import com.luxoft.sdemenkov.movielend.models.Genre;

/**
 * Created by sergeydemenkov on 28.10.17.
 */
public class ResponceGetAllGenres {
    int id;
    String name;

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
