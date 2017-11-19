package com.luxoft.sdemenkov.movieland.web.response;

/**
 * Created by sergeydemenkov on 15.11.2017.
 */
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
