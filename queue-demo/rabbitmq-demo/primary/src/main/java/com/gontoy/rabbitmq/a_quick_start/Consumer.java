package com.gontoy.rabbitmq.a_quick_start;

import com.rabbitmq.client.*;

/**
 * 消费者：接收消息
 * @author gzw
 */
public class Consumer {
    public static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂 ip，连接队列
        factory.setHost("124.222.14.57");

        // 设置用户名和密码
        factory.setUsername("gzwrrr");
        factory.setPassword("963gzw@wl.");

        // 创建连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();


        // 接收消息的回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };

        // 取消消息的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消费消息被中断");
        };

        /**
         * 消费者消费消息，参数的含义
         *  1.消费哪个队列
         *  2.消费成功之后是否要自动应答，true 代表自动应答
         *  3.消费者未成功消费的回调
         *  4.消费者取消消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
