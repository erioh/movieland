package com.luxoft.sdemenkov.movieland.web.util;


import com.luxoft.sdemenkov.movieland.model.business.Rate;
import com.luxoft.sdemenkov.movieland.model.business.User;
import com.luxoft.sdemenkov.movieland.web.dto.request.RateDto;

public class RateBuilder {
    private Rate rate;

    private RateBuilder() {
    }

    public static RateBuilder from(RateDto rateDto) {
        RateBuilder builder = new RateBuilder();
        Rate rate = new Rate();
        rate.setRating(rateDto.getRating());
        builder.rate = rate;
        return builder;
    }

    public RateBuilder withMovieId(Integer movieId) {
        this.rate.setMovieId(movieId);
        return this;
    }

    public RateBuilder withUser(User user) {
        this.rate.setUserId(user.getId());
        return this;
    }

    public Rate build() {
        return this.rate;
    }
}
