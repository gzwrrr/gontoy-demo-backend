package com.gontoy.jedis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTemplateTests {

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void connectTest() {
        System.out.println(redisTemplate.getClientList());
    }

}
