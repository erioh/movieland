package com.luxoft.sdemenkov.movieland.web.response;

/**
 * Created by sergeydemenkov on 11.11.17.
 */
public class ExceptionMessageDto {
    private String exceptionMessage;

    public ExceptionMessageDto(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }
}
