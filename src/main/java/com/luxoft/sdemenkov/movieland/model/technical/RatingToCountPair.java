package com.luxoft.sdemenkov.movieland.model.technical;


public class RatingToCountPair {
    private double ratingSum;
    private int count;

    public RatingToCountPair() {
    }

    public RatingToCountPair(double ratingSum, int count) {
        this.ratingSum = ratingSum;
        this.count = count;
    }

    public double getRatingSum() {
        return ratingSum;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "RatingToCountPair{" +
                "ratingSum=" + ratingSum +
                ", count=" + count +
                '}';
    }
}
