package com.luxoft.sdemenkov.movieland.web.response;


public class LogoutMessageDto {
    private String message;

    public LogoutMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
