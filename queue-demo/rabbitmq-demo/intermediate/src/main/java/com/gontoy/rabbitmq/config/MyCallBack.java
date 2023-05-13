package com.gontoy.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * 处理异常情况的回调配置类
 * @author gzw
 */
@Slf4j
@Configuration
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    /**
     * 注入
     */
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 交换机确认回调的方法
     * 1.发消息：交换机接收到了：
     *  - correlationData 保存回调消息的 ID 及相关信息
     *  - 交换机接收到消息 ack = true
     *  - cause null
     * 2.发消息：交换机接收失败了：
     *  - correlationData 保存回调消息的 ID 及相关信息
     *  - 交换机接收到消息 ack = false
     *  - cause 失败的原因
     * @param correlationData 保存回调消息的 ID 及相关信息
     * @param ack ack
     * @param cause cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到 ID 为：{} 的消息", id);
        } else {
            log.info("交换机还未受到 ID 为：{} 的消息，原因：{}", id, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("消息：{}，被交换机：{} 退回，退回原因：{}，路由 key：{}", new String(message.getBody()), exchange, replyText, routingKey);
    }
}
