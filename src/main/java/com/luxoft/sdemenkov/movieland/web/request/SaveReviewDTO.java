package com.luxoft.sdemenkov.movieland.web.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.luxoft.sdemenkov.movieland.web.mapper.SaveReviewDtoMapper;

@JsonDeserialize(using = SaveReviewDtoMapper.class)
public class SaveReviewDTO {
    private int movieId;
    private String text;

    public int getMovieId() {
        return movieId;
    }

    public SaveReviewDTO setMovieId(int movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getText() {
        return text;
    }

    public SaveReviewDTO setText(String text) {
        this.text = text;
        return this;
    }
}
