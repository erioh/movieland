package com.luxoft.sdemenkov.movieland.settings;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource({"classpath:property/cron.properties"})
public class CacheSettings {
}
