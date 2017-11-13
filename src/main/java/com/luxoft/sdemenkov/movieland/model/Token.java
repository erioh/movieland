package com.luxoft.sdemenkov.movieland.model;

import java.util.UUID;


public class Token {
    private String nickname;
    private UUID uuid;
    private long birthTime;

    public Token(User user, UUID uuid, long birthTime) {
        this.nickname = user.getNickname();
        this.uuid = uuid;
        this.birthTime = birthTime;
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

    public long getBirthTime() {
        return birthTime;
    }


    public void setBirthTime(long birthTime) {
        this.birthTime = birthTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "nickname='" + nickname + '\'' +
                ", uuid=" + uuid +
                ", birthTime=" + birthTime +
                '}';
    }

}
