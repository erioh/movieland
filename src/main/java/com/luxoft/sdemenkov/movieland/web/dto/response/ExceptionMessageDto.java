package com.luxoft.sdemenkov.movieland.web.dto.response;


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
