package com.luxoft.sdemenkov.movieland.web.exception;

public class WrongRateValueException extends RuntimeException {
    public WrongRateValueException(double rating) {
        super("Wrong rating was used. It sould be from 1 to 10. Used rating = " + rating);
    }
}
