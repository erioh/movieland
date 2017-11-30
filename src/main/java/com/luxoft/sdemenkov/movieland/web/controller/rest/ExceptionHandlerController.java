package com.luxoft.sdemenkov.movieland.web.controller.rest;

import com.luxoft.sdemenkov.movieland.web.dto.response.ExceptionMessageDto;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import com.luxoft.sdemenkov.movieland.web.exception.WrongRateValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(WrongMovieIdException.class)
    public ResponseEntity<?> handleWrongMovieIdException(WrongMovieIdException e){
        return new ResponseEntity<ExceptionMessageDto>( new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(WrongRateValueException.class)
    public ResponseEntity<?> handleWrongRateValueException(WrongRateValueException e){
        return new ResponseEntity<ExceptionMessageDto>( new ExceptionMessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
