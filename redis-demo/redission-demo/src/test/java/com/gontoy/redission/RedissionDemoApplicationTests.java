package com.gontoy.redission;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedissionDemoApplicationTests {


    @Autowired
    Config config;

    @Test
    void masterSlaveServers() {
        config.useMasterSlaveServers()
                .setMasterAddress("redis://192.168.30.201:6380")
                .addSlaveAddress("redis://192.168.30.201:6381", "redis://192.168.30.201:6382");
        RedissonClient redissonClient = Redisson.create(config);
        System.out.println(redissonClient.getKeys());
    }

    @Test
    void sentinelServers() {
        config.useSentinelServers()
                .setMasterName("mymaster")
                .addSentinelAddress("redis://192.168.30.201:26380", "redis://192.168.30.201:26381", "redis://192.168.30.201:26382");
        RedissonClient redissonClient = Redisson.create(config);
    }


    @Test
    void clusterServers() {
        config.useClusterServers()
                .setScanInterval(2000)
                .addNodeAddress(
                        "redis://192.168.30.201:6390",
                        "redis://192.168.30.201:6391",
                        "redis://192.168.30.201:6392",
                        "redis://192.168.30.201:6393",
                        "redis://192.168.30.201:6394",
                        "redis://192.168.30.201:6395"
                        );
        RedissonClient redissonClient = Redisson.create(config);
    }



}
