package com.luxoft.sdemenkov.movieland.web.response;

/**
 * Created by sergeydemenkov on 11.11.17.
 */
public class StringDto {
    private String response;

    public StringDto(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
