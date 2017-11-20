package com.luxoft.sdemenkov.movieland.model.technical;

import com.luxoft.sdemenkov.movieland.model.business.User;

import java.time.LocalDateTime;
import java.util.UUID;


public class Token {
    private UUID uuid;
    private LocalDateTime dieTime;
    private User user;

    public Token(User user, UUID uuid, LocalDateTime dieTime) {
        this.user = user;
        this.uuid = uuid;
        this.dieTime = dieTime;
    }

    public Token(User user) {
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public LocalDateTime getDieTime() {
        return dieTime;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Token{" +
                "uuid=" + uuid +
                ", dieTime=" + dieTime +
                ", user=" + user +
                '}';
    }
}
