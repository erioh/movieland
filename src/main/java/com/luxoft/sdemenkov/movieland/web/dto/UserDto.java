package com.luxoft.sdemenkov.movieland.web.dto;


import com.luxoft.sdemenkov.movieland.model.business.User;

public class UserDto {
    private int id;
    private String nickname;

    public UserDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
