package com.luxoft.sdemenkov.movieland.web.exception;

import org.springframework.http.HttpStatus;


public class NeededRolesAneAbsentException extends RestException {
    public NeededRolesAneAbsentException() {
        super("User doesn't have needed roles");
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
