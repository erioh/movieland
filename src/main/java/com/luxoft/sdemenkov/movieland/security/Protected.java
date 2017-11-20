package com.luxoft.sdemenkov.movieland.security;

import com.luxoft.sdemenkov.movieland.security.role.Role;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protected {
    Role protectedBy();
}
