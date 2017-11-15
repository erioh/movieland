package com.luxoft.sdemenkov.movieland.settings;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource({"classpath:property/cron.properties"})
@ComponentScan(basePackages = "com.luxoft.sdemenkov.movieland")
public class CacheSettings {
}
