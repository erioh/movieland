package com.luxoft.sdemenkov.movieland.web.response;

/**
 * Created by sergeydemenkov on 15.11.2017.
 */
public class ResponseMessageDto {
    private String message;

    public ResponseMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
