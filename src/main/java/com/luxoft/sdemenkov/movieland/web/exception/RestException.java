package com.luxoft.sdemenkov.movieland.web.exception;

import org.springframework.http.HttpStatus;

public abstract class RestException extends RuntimeException {


    public RestException(String s) {
        super(s);
    }

    public abstract HttpStatus getHttpStatus();
}
