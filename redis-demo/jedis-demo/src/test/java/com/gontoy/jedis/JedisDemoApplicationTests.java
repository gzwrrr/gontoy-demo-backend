// package com.gontoy.jedis;
//
//
// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;
// import redis.clients.jedis.*;
//
// import java.util.HashSet;
// import java.util.Set;
//
// /**
//  * 需要使用「不与 Spring Boot 进行整合的依赖」
//  * 在 Pom 文件中切换即可
//  */
// @SpringBootTest
// class JedisDemoApplicationTests {
//
//     /**
//      * 连接
//      */
//     @Test
//     public void connectTest() {
//         Jedis jedis = new Jedis("192.168.30.201", 6382);
//         jedis.set("aaa", "aaa");
//         jedis.set("aaa1", "aaa1");
//         jedis.set("aaa2", "aaa2");
//         jedis.set("aaa3", "aaa3");
//         System.out.println(jedis.keys("*"));
//         jedis.close();
//     }
//
//
//     /**
//      * 使用连接池
//      */
//     @Test
//     public void poolTest() {
//         JedisPool jedisPool = new JedisPool("192.168.30.201", 6382);
//         try (Jedis jedis = jedisPool.getResource()) {
//             jedis.set("aaa", "aaa");
//             jedis.set("aaa1", "aaa1");
//             jedis.set("aaa2", "aaa2");
//             jedis.set("aaa3", "aaa3");
//             System.out.println(jedis.keys("*"));
//         }
//     }
//
//     /**
//      * 连接池更为简便的使用
//      */
//     @Test
//     public void pooledTest() {
//         JedisPooled jedis = new JedisPooled("192.168.30.201", 6382);
//         jedis.set("aaa", "aaa");
//         jedis.set("aaa1", "aaa1");
//         jedis.set("aaa2", "aaa2");
//         jedis.set("aaa3", "aaa3");
//         System.out.println(jedis.keys("*"));
//     }
//
//     /**
//      * 连接哨兵集群
//      */
//     @Test
//     public void sentinelTest() {
//         Set<String> sentinels = new HashSet<>();
//         sentinels.add("192.168.30.201:26380");
//         sentinels.add("192.168.30.201:26381");
//         sentinels.add("192.168.30.201:26382");
//         JedisSentinelPool jedis = new JedisSentinelPool("mymaster", sentinels);
//         jedis.getCurrentHostMaster();
//     }
//
//     /**
//      * 连接 cluster
//      */
//     @Test
//     public void clusterTest() {
//         Set<HostAndPort> cluster = new HashSet<>();
//         cluster.add(new HostAndPort("192.168.30.201", 6390));
//         cluster.add(new HostAndPort("192.168.30.201", 6391));
//         cluster.add(new HostAndPort("192.168.30.201", 6392));
//         cluster.add(new HostAndPort("192.168.30.201", 6393));
//         cluster.add(new HostAndPort("192.168.30.201", 6394));
//         cluster.add(new HostAndPort("192.168.30.201", 6395));
//
//         JedisCluster jedis = new JedisCluster(cluster);
//         System.out.println(jedis.get("aaa"));
//     }
//
//     /**
//      * 事务
//      */
//     @Test
//     public void transactionTest() {
//         JedisPool jedisPool = new JedisPool("192.168.30.201", 6382);
//         try (Jedis jedis = jedisPool.getResource()) {
//
//             Transaction ts = jedis.multi();
//             try {
//                 jedis.set("ccccc", "cccc1");
//                 int i = 3 / 0;
//                 jedis.set("ccccc1", "cccc2");
//                 ts.exec();
//             } catch (Exception e) {
//                 ts.discard();
//             }
//
//             jedis.set("bbb2", "bbb2");
//             jedis.set("bbb3", "bbb3");
//             System.out.println(jedis.keys("*"));
//         }
//     }
//
//     /**
//      * 事务 watch
//      */
//     @Test
//     public void transactionWatchTest() {
//         JedisPool jedisPool = new JedisPool("192.168.30.201", 6382);
//         try (Jedis jedis = jedisPool.getResource()) {
//
//             jedis.watch("aaa");
//             Transaction ts = jedis.multi();
//             try {
//                 jedis.set("aaa", "aaaaaa");
//                 ts.exec();
//             } catch (Exception e) {
//                 ts.discard();
//             }
//
//             System.out.println(jedis.get("aaa"));
//         }
//     }
//
//
// }
