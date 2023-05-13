package com.gontoy.rabbitmq.controller;

import com.gontoy.rabbitmq.config.DelayedQueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 发送延迟消息
 * @author gzw
 */
@RestController
@RequestMapping("/ttl")
public class SendMessageController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("sendMsg/{message}")
    public void sendMessage(@PathVariable String message) {
        logger.info("当前时间: {}，发送一条消息给两个 TTL 队列: {}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "XA", String.format("消息来自 TTL 为 10s 的队列：", message));
        rabbitTemplate.convertAndSend("X", "XB", String.format("消息来自 TTL 为 40s 的队列：", message));
    }

    @GetMapping("/sendExpirationMsg/{message}/{ttl}")
    public void sendMessage(@PathVariable String message, @PathVariable String ttl) {
        logger.info("当前时间: {}，发送一条延迟 {} ms 的消息给队列 QC: {}", new Date().toString(), ttl, message);
        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        });
    }

    @GetMapping("/sendDelayedMsg/{message}/{delay}")
    public void sendDelayMessage(@PathVariable String message, @PathVariable Integer delay) {
        logger.info("当前时间: {}，发送一条延迟 {} ms 的消息给延迟队列（由交换机延时）: {}", new Date().toString(), delay, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message, msg -> {
            msg.getMessageProperties().setDelay(delay);
            return msg;
        });
    }
}
