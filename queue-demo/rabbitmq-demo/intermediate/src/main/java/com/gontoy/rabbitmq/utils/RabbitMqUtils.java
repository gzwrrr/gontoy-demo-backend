package com.gontoy.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ 连接工厂创建信道的工具类
 * @author gzw
 */
public class RabbitMqUtils {

    public static Channel getChannel() throws Exception {
        // 创建一个连接工厂
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
        return channel;
    }
}
