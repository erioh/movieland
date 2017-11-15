package com.luxoft.sdemenkov.movieland.model;


public class User {
    private int id;
    private String nickname;
    private String email;

    public User() {
    }

    public User(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
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
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
