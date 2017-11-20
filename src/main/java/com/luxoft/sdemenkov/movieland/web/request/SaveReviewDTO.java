package com.luxoft.sdemenkov.movieland.web.request;

public class SaveReviewDto {
    private int movieId;
    private String text;

    public int getMovieId() {
        return movieId;
    }

    public SaveReviewDto setMovieId(int movieId) {
        this.movieId = movieId;
        return this;
    }

    public String getText() {
        return text;
    }

    public SaveReviewDto setText(String text) {
        this.text = text;
        return this;
    }

    @Override
    public String toString() {
        return "SaveReviewDto{" +
                "movieId=" + movieId +
                ", text='" + text + '\'' +
                '}';
    }
}
