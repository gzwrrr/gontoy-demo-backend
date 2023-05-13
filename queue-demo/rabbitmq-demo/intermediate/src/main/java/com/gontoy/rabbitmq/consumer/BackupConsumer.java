package com.gontoy.rabbitmq.consumer;

import com.gontoy.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 发布确认（高级）
 * 备份，消费者
 * @author gzw
 */
@Slf4j
@Component
public class BackupConsumer {
    @RabbitListener(queues = ConfirmConfig.BACKUP_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.info("备份消息：{}", msg);
    }
}
