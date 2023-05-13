package com.gontoy.rabbitmq.g_dead_letter_queue;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import java.nio.charset.StandardCharsets;
/**
 * 死信队列
 * 死信消费者
 * @author gzw
 */
public class Consumer02 {
    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.printf("Consumer01 接收的消息是：%s\n", new String(message.getBody(), StandardCharsets.UTF_8));
        };

        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
