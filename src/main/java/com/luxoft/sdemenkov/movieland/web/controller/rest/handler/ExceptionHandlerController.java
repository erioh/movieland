package com.luxoft.sdemenkov.movieland.web.controller.rest.handler;

import com.luxoft.sdemenkov.movieland.web.dto.response.ExceptionMessageDto;
import com.luxoft.sdemenkov.movieland.web.exception.WrongMovieIdException;
import com.luxoft.sdemenkov.movieland.web.exception.WrongRateValueException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongMovieIdException.class)
    public ExceptionMessageDto handleWrongMovieIdException(WrongMovieIdException e){
        return new ExceptionMessageDto(e.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WrongMovieIdException.class)
    public ExceptionMessageDto handleWrongRateValueException(WrongRateValueException e){
        return new ExceptionMessageDto(e.getMessage());
    }
}
