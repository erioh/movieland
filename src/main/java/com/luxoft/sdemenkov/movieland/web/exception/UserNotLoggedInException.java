package com.luxoft.sdemenkov.movieland.web.exception;


import org.springframework.http.HttpStatus;

public class UserNotLoggedInException extends RestException {
    public UserNotLoggedInException() {
        super("User is not logged in");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
