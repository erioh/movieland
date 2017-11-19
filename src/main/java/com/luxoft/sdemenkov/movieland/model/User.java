package com.luxoft.sdemenkov.movieland.model;


import com.luxoft.sdemenkov.movieland.security.role.Role;

import java.util.List;

public class User {
    private int id;
    private String nickname;
    private String email;
    private List<Role> roleList;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }
}
