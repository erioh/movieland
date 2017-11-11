package com.luxoft.sdemenkov.movieland.web.response;

import com.luxoft.sdemenkov.movieland.model.Token;

import java.util.UUID;



public class TokenDTO {
    private UUID uuid;
    String nickname;

    public TokenDTO(Token token) {
        this.uuid = token.getUuid();
        this.nickname = token.getNickname();
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
        return "TokenDTO{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
