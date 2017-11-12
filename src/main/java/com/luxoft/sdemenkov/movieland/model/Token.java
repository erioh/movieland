package com.luxoft.sdemenkov.movieland.model;

import java.util.UUID;


public class Token {
    String nickname;
    private UUID uuid;

    public Token(UUID uuid, String nickname) {
        this.uuid = uuid;
        this.nickname = nickname;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
