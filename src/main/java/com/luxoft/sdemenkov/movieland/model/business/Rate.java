package com.luxoft.sdemenkov.movieland.model.business;


public class Rate {
    private double rating;
    private int movieId;
    private int userId;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "rating=" + rating +
                ", movieId=" + movieId +
                ", userId=" + userId +
                '}';
    }
}