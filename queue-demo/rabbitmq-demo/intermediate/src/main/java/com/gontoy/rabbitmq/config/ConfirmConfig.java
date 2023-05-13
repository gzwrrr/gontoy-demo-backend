package com.gontoy.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 发布确认（高级）配置类
 * @author gzw
 */
@Configuration
public class ConfirmConfig {
    // 队列
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";

    // 交换机
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";

    // routing key
    public static final String CONFIRM_ROUTING_KEY = "confirm_routingkey";

    // 备份队列
    public static final String BACKUP_QUEUE_NAME = "backup_queue";

    // 备份交换机
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";

    // 报警队列
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    @Bean
    public DirectExchange confirmExchange() {
        // 备份到备份交换机
        return ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE_NAME)
                .durable(true)
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE_NAME).build();
    }

    @Bean
    public Binding queueBindingExchange(@Qualifier("confirmQueue") Queue confirmQueue,
                                        @Qualifier("confirmExchange") DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }

    /**
     * 备份交换机和警告交换机
     */
    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    @Bean
    public Binding backupQueueBindingExchange(@Qualifier("backupQueue") Queue backupQueue,
                                              @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding warningQueueBindingExchange(@Qualifier("warningQueue") Queue warningQueue,
                                              @Qualifier("backupExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
