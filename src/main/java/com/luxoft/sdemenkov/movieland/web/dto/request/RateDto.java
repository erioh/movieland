package com.luxoft.sdemenkov.movieland.web.dto.request;


public class RateDto {
    private double rating;

    public RateDto(double rating) {
        this.rating = rating;
    }

    public RateDto() {
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "RateDto{" +
                "rating=" + rating +
                '}';
    }
}
