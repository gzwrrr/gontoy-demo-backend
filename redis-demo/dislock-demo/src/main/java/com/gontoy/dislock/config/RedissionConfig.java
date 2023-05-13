package com.gontoy.dislock.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissionConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;


    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(String.format("redis://%s:%d", redisHost, redisPort))
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

    @Bean("sentinel01")
    public Redisson sentinel01() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress(
                        String.format("redis://%s:%d", redisHost, 16380),
                        String.format("redis://%s:%d", redisHost, 16381),
                        String.format("redis://%s:%d", redisHost, 16382)
                )
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

    @Bean("sentinel02")
    public Redisson sentinel02() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress(
                        String.format("redis://%s:%d", redisHost, 26380),
                        String.format("redis://%s:%d", redisHost, 26381),
                        String.format("redis://%s:%d", redisHost, 26382)
                )
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }

    @Bean("sentinel03")
    public Redisson sentinel03() {
        Config config = new Config();
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress(
                        String.format("redis://%s:%d", redisHost, 36380),
                        String.format("redis://%s:%d", redisHost, 36381),
                        String.format("redis://%s:%d", redisHost, 36382)
                )
                .setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
