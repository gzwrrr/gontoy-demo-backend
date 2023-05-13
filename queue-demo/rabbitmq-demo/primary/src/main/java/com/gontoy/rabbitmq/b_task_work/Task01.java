package com.gontoy.rabbitmq.b_task_work;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;

import java.util.Scanner;

/**
 * 任务线程
 * 发送大量的消息
 * @author gzw
 */
public class Task01 {
    private static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 从控制台中接收信息
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String msg = scanner.next();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }
    }
}
