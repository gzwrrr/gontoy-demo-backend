package com.gontoy.rabbitmq.f_exchange.b_direct;

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
public class ReceiveLogsDirect02 {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = "console";

        // 声明一个队列
        channel.queueDeclare(queueName, false, false, false, null);

        /**
         * 绑定交换机与队列
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "error");
        System.out.println("等待接收消息...");

        // 接收到消息的回调
        DeliverCallback deliverCallback = (deliveryTag, message) -> {
            System.out.printf("ReceiveLogsDirect02 接收到消息：%s\n", new String(message.getBody(), StandardCharsets.UTF_8));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
