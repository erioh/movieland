package com.luxoft.sdemenkov.movieland.web.exception;


public class WrongMovieIdException extends RuntimeException {
    public WrongMovieIdException(int movieId) {
        super("Wrong movieId = " + movieId);
    }
}
