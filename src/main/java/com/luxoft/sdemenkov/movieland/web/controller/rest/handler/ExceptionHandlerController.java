package com.luxoft.sdemenkov.movieland.web.controller.rest.handler;

import com.luxoft.sdemenkov.movieland.web.dto.response.ExceptionMessageDto;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import com.luxoft.sdemenkov.movieland.web.exception.WrongRateValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(WrongMovieIdException.class)
    public ResponseEntity<?> handleWrongMovieIdException(WrongMovieIdException e) {
        return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongRateValueException.class)
    public ResponseEntity<?> handleWrongRateValueException(WrongRateValueException e) {
        return new ResponseEntity<>(new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
