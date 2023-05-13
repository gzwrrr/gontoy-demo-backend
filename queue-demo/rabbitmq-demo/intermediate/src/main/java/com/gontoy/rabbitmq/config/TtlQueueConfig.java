package com.gontoy.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 具有过期时间的队列
 * @author gzw
 */
@Configuration
public class TtlQueueConfig {
    /**
     * 普通交换机的名称
     */
    public static final String X_EXCHANGE = "X";

    /**
     * 死信交换机的名称
     */
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    /**
     * 普通队列的名称
     */
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";

    /**
     * 死信队列的名称
     */
    public static final String DEAD_QUEUE = "QD";

    /**
     * 声明普通交换机 xExchange
     */
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明死信交换机 yExchange
     */
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明普通队列 queueA，TTL 为 10s
     */
    @Bean("queueA")
    public Queue queueA() {
        final int TTL = 10000;
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", TTL);
        return QueueBuilder.durable(QUEUE_A).withArguments(arguments).build();
    }

    /**
     * 声明普通队列 queueB，TTL 为 40s
     */
    @Bean("queueB")
    public Queue queueB() {
        final int TTL = 40000;
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        arguments.put("x-message-ttl", TTL);
        return QueueBuilder.durable(QUEUE_B).withArguments(arguments).build();
    }

    /**
     * 声明普通队列 queueC，适用于任何延迟的队列，不需要设置延迟
     */
    @Bean("queueC")
    public Queue queueC() {
        Map<String, Object> arguments = new HashMap<>(3);
        arguments.put("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE);
        arguments.put("x-dead-letter-routing-key", "YD");
        return QueueBuilder.durable(QUEUE_C).withArguments(arguments).build();
    }

    /**
     * 声明死信队列 queueD
     */
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    /**
     * 绑定 queueA 与 xExchange
     */
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");
    }

    /**
     * 绑定 queueB 与 xExchange
     */
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");
    }

    /**
     * 绑定 queueB 与 xExchange
     */
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");
    }

    /**
     * 绑定 queueD 与 yExchange
     */
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");
    }
}
