package com.gontoy.rabbitmq.a_quick_start;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者：发消息
 * @author gzw
 */
public class Producer {
    public static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception {
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

        /**
         * 生成一个队列，各个参数的含义：
         *  1.队列的名称
         *  2.队列中的消息是否持久化，默认情况是将消息存储在内存中
         *  3.该队列是否只供一个消费者进行消费，是否进行消息共享，false 代表只能一个消费者消费
         *  4.是否自动删除，最后一个消费者端开启连接之后，该队列是否自动删除，false 表示不自动删除
         *  5.其他参数
         */
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 发消息
        String message = "Hello World!";

        /**
         * 发送一个消息，参数的含义如下
         *  1.发送到那个交换机
         *  2.路由的 key 值是哪个，本次是队列的名称
         *  3.其他参数信息
         *  4.消息体，二进制形式
         */
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

        System.out.println("消息发送完毕");
    }
}
