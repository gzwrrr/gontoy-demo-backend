package com.gontoy.rabbitmq.g_dead_letter_queue;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

/**
 * 死信队列
 * 生产者
 * @author gzw
 */
public class Producer {
    // 生产者只需要知道普通交换机
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 死信消息，设置 TTL 时间
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();

        for (int i = 0; i < 10; i++) {
            String message = String.format("info：%d", i + 1);
            channel.basicPublish(NORMAL_EXCHANGE, "normal-routing", properties, message.getBytes());
        }
    }
}
