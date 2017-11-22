package com.luxoft.sdemenkov.movieland.web.dto.response;

import com.luxoft.sdemenkov.movieland.model.technical.Token;


public class TokenDto {
    private String nickname;
    private String uuid;

    public TokenDto(Token token) {
        this.uuid = token.getUuid().toString();
        this.nickname = token.getUser().getNickname();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
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
        return "TokenDto{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
