package com.gontoy.rabbitmq.f_exchange.c_topic;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * 接收消息
 * 消费者
 * @author gzw
 */
public class ReceiveLogsTopic02 {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = "topic_2";

        // 声明一个队列
        channel.queueDeclare(queueName, false, false, false, null);

        /**
         * 绑定交换机与队列
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName, EXCHANGE_NAME, "lazy.#");
        System.out.println("等待接收消息...");

        // 接收到消息的回调
        DeliverCallback deliverCallback = (deliveryTag, message) -> {
            System.out.printf("ReceiveLogsTopic02 接收到消息：%s\n", new String(message.getBody(), StandardCharsets.UTF_8));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
