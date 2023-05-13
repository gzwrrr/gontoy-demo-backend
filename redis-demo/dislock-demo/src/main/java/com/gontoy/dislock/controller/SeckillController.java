package com.gontoy.dislock.controller;

import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class SeckillController {

    @Autowired
    private StringRedisTemplate srt;

    @Value("${server.port}")
    private String serverPort;

    /**
     * 加锁在分布式场景下无法解决超卖的问题
     */
    @GetMapping("/sk1")
    public String seckillHandler1() {
        int res = 0;
        synchronized (this) {
            String stock = srt.opsForValue().get("sk:0008");
            int amount = stock == null ? 0 : Integer.parseInt(stock);
            if (amount > 0) {
                // 修改库存后再写回 redis
                srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                res = amount;
            }
        }
        System.out.println("库存剩余: " + res);
        return "库存剩余: " + res;
    }


    private static final String REDIS_LOCK = "SECKILL_LOCK";

    /**
     * 分布式锁
     */
    @GetMapping("/sk2")
    public String seckillHandler2() {
        String clientId = UUID.randomUUID().toString();

        try {
            // 添加锁
            // 一定要设置过期时间，如果不设置服务挂掉了会一直阻塞
            // 注意设置的时候要加锁和设置超时一起，这两个是一个原子操作，即不要分开设置
            // 但是如果业务处理时间过长，超过了设置的过期时间，也是有可能出现问题的
            Boolean lock = srt.opsForValue().setIfAbsent(REDIS_LOCK, clientId, 5, TimeUnit.SECONDS);

            if (lock) {
                String stock = srt.opsForValue().get("sk:0008");
                int amount = stock == null ? 0 : Integer.parseInt(stock);
                if (amount > 0) {
                    // 修改库存后再写回 redis
                    srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                    System.out.println("库存剩余: " + amount);
                    return "库存剩余: " + amount;
                }
            }
        } finally {
            // 一定要删除，否则可能一直阻塞
            // 只有添加锁客户端才能释放锁
            // 但是要注意原子操作，否则可能会释放其他客户端的锁，所以下面的写法有问题
            if (srt.opsForValue().get(REDIS_LOCK).equals(clientId)) {
                srt.delete(REDIS_LOCK);
            }
        }

        System.out.println("没有抢到锁");
        return "没有抢到锁";
    }


    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private Integer redisPort;


    /**
     * 分布式锁 + Lua 脚本
     */
    @GetMapping("/sk3")
    public String seckillHandler3() {
        String clientId = UUID.randomUUID().toString();
        try {
            // 添加锁
            // 一定要设置过期时间，如果不设置服务挂掉了会一直阻塞
            // 注意设置的时候要加锁和设置超时一起，这两个是一个原子操作，即不要分开设置
            // 但是如果业务处理时间过长，超过了设置的过期时间，也是有可能出现问题的
            Boolean lock = srt.opsForValue().setIfAbsent(REDIS_LOCK, clientId, 5, TimeUnit.SECONDS);

            if (lock) {
                String stock = srt.opsForValue().get("sk:0008");
                int amount = stock == null ? 0 : Integer.parseInt(stock);
                if (amount > 0) {
                    // 修改库存后再写回 redis
                    srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                    System.out.println("库存剩余: " + amount);
                    return "库存剩余: " + amount;
                }
            }
        } finally {
            // Lua 脚本能够保证原子性
            // 但是注意，此时如果业务时间大于设置的过期时间还是可能出问题，可以使用可重入锁 + 锁续命解决，直接使用 Redisson 会方便很多
            JedisPool jedisPool = new JedisPool(redisHost, redisPort);
            try (Jedis jedis = jedisPool.getResource()) {
                String script = "if redis.call('get', KEYS[1]) == ARGV[1] " +
                                "then return redis.call('del', KEYS[1]) " +
                                "end " +
                                "return 0 ";
                Object eval = jedis.eval(script, Collections.singletonList(REDIS_LOCK), Collections.singletonList(clientId));
                if ("1".equals(eval.toString())) {
                    System.out.println("释放锁成功");
                } else {
                    System.out.println("释放锁失败");
                }
            }
        }

        System.out.println("没有抢到锁");
        return "没有抢到锁";
    }


    @Autowired
    private Redisson redisson;

    /**
     * Redission 分布式锁
     */
    @GetMapping("/sk4")
    public String seckillHandler4() {
        RLock lock = redisson.getLock(REDIS_LOCK);
        try {
            // 添加锁
            Boolean locked = lock.tryLock();
            if (locked) {
                String stock = srt.opsForValue().get("sk:0008");
                int amount = stock == null ? 0 : Integer.parseInt(stock);
                if (amount > 0) {
                    // 修改库存后再写回 redis
                    srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                    System.out.println("库存剩余: " + amount);
                    return "库存剩余: " + amount;
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println("没有抢到锁");
        return "没有抢到锁";
    }


    @Resource(name = "sentinel01")
    private Redisson sentinel01;

    @Resource(name = "sentinel02")
    private Redisson sentinel02;

    @Resource(name = "sentinel03")
    private Redisson sentinel03;

    /**
     * Redission 红锁
     */
    @GetMapping("/sk5")
    public String seckillHandler5() {
        // 可重入锁
        RLock rLock1 = sentinel01.getLock(REDIS_LOCK);
        RLock rLock2 = sentinel02.getLock(REDIS_LOCK);
        RLock rLock3 = sentinel03.getLock(REDIS_LOCK);
        // 红锁
        RLock lock = new RedissonRedLock(rLock1, rLock2, rLock3);
        try {
            // 添加锁
            Boolean locked = lock.tryLock();
            if (locked) {
                String stock = srt.opsForValue().get("sk:0008");
                int amount = stock == null ? 0 : Integer.parseInt(stock);
                if (amount > 0) {
                    // 修改库存后再写回 redis
                    srt.opsForValue().set("sk:0008", String.valueOf(--amount));
                    System.out.println("库存剩余: " + amount);
                    return "库存剩余: " + amount;
                }
            }
        } finally {
            lock.unlock();
        }
        System.out.println("没有抢到锁");
        return "没有抢到锁";
    }

}


