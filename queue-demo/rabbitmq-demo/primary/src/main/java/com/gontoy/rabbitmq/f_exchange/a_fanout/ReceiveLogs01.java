package com.gontoy.rabbitmq.f_exchange.a_fanout;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * 接收消息
 * 消费者
 * @author gzw
 */
public class ReceiveLogs01 {

    // 交换机的名称
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        /**
         * 生成一个临时队列，队列的名称是随机的
         * 当消费者断开于队列的连接时，队列自动删除
         */
        String queueName = channel.queueDeclare().getQueue();

        /**
         * 绑定交换机与队列
         */
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println("等待接收消息...");

        // 接收到消息的回调
        DeliverCallback deliverCallback = (deliveryTag, message) -> {
            System.out.printf("ReceiveLogs01 接收到消息：%s\n", new String(message.getBody(), StandardCharsets.UTF_8));
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});
    }
}
