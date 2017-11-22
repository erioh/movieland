package com.luxoft.sdemenkov.movieland.web.dto.request;
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
        return "saveReviewDto{" +
                "movieId=" + movieId +
                ", text='" + text + '\'' +
                '}';
    }
}
