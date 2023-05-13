package com.gontoy.rabbitmq.c_manual_response;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @author gzw
 */
public class Task02 {
    private static final String QUEUE_NAME = "ACK";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 从控制台中接收信息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String msg = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes(StandardCharsets.UTF_8));
            System.out.printf("生产者发出消息：%s\n", msg);
        }
    }
}
