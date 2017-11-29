package com.luxoft.sdemenkov.movieland.model.technical;


public class RatingToCounPair {
    private final double ratingSum;
    private final int count;

    public RatingToCounPair(double ratingSum, int count) {
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
        return "RatingToCounPair{" +
                "ratingSum=" + ratingSum +
                ", count=" + count +
                '}';
    }
}
