package com.luxoft.sdemenkov.movieland.model;

import java.time.LocalDateTime;
import java.util.UUID;


public class Token {
    private String nickname;
    private UUID uuid;
    private LocalDateTime dieTime;

    public Token(User user, UUID uuid, LocalDateTime dieTime) {
        this.nickname = user.getNickname();
        this.uuid = uuid;
        this.dieTime = dieTime;
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

    public LocalDateTime getDieTime() {
        return dieTime;
    }


    public void setDieTime(LocalDateTime dieTime) {
        this.dieTime = dieTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "nickname='" + nickname + '\'' +
                ", uuid=" + uuid +
                ", dieTime=" + dieTime +
                '}';
    }

}
