package com.gontoy.rabbitmq.b_task_work;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 工作线程，相当于之前的消费者
 * 验证一个消息只能被一个线程消费一次
 * @author gzw
 */
public class Worker01 {
    private static final String QUEUE_NAME = "Hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.printf("接收到的消息：%s\n", new String(message.getBody()));
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.printf("%s：消费者取消消费接口的逻辑", consumerTag);
        };

        // 接收消息
        System.out.println("c2 等待接收消息...");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);


    }
}
