package com.luxoft.sdemenkov.movieland.service;

import com.luxoft.sdemenkov.movieland.model.User;

/**
 * Created by sdemenkov on 09.11.2017.
 */
public interface SecurityService {
    User loginAsUser(String email, String password);
}
