package com.gontoy.redission.config;

import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {

    @Bean
    public Config config() {
        return new Config();
    }
}
