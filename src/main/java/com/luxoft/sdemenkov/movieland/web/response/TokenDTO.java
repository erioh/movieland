package com.luxoft.sdemenkov.movieland.web.response;

import com.luxoft.sdemenkov.movieland.model.Token;


public class TokenDTO {
    private String nickname;
    private String uuid;

    public TokenDTO(Token token) {
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
        return "TokenDTO{" +
                "uuid=" + uuid +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
