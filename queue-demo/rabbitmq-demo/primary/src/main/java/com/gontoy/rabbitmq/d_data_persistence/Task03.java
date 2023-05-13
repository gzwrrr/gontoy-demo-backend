package com.gontoy.rabbitmq.d_data_persistence;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author gzw
 */
public class Task03 {
    private static final String QUEUE_NAME = "ACK";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明队列，设置持久化
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);

        // 从控制台中接收信息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String msg = scanner.next();
            // 发送消息并将消息持久化
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes(StandardCharsets.UTF_8));
            System.out.printf("生产者发出消息：%s\n", msg);
        }
    }
}
