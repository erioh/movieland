package com.luxoft.sdemenkov.movielend.web.controller;

import com.luxoft.sdemenkov.movielend.web.responce.ResponseGetAllGenres;
import com.luxoft.sdemenkov.movielend.web.responce.ResponseGetAllMovies;
import com.luxoft.sdemenkov.movielend.web.responce.ResponseGetThreeRandomMovies;

import java.util.List;

/**
 * Created by dp-ptcstd-43 on 11/1/2017.
 */
public interface MovieController {
    public List<ResponseGetAllMovies> getAllMovies();
    public List<ResponseGetThreeRandomMovies> getThreeRandomMovies();
    public List<ResponseGetAllGenres> getAllGenres();
}
