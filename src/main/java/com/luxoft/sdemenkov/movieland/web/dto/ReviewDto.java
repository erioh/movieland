package com.luxoft.sdemenkov.movieland.web.dto;

import com.luxoft.sdemenkov.movieland.model.business.Review;

public class ReviewDto {
    private int id;
    private UserDto user;
    private String text;

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.user = new UserDto(review.getUser());
        this.text = review.getText();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ReviewDto{" +
                "id=" + id +
                ", user=" + user +
                ", text='" + text + '\'' +
                '}';
    }
}
