package com.luxoft.sdemenkov.movieland.settings;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:property/app.properties")
public class GlobalAppSettings {
}
