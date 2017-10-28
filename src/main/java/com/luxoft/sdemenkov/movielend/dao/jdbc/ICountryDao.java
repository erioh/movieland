package com.luxoft.sdemenkov.movielend.dao.jdbc;

import com.luxoft.sdemenkov.movielend.models.Country;
import com.luxoft.sdemenkov.movielend.models.Movie;

import java.util.List;

public interface ICountryDao {
    List<Country> getCountryListByMovie(Movie movie);
}
