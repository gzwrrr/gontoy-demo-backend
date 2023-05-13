package com.gontoy.rabbitmq.c_manual_response;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.gontoy.rabbitmq.utils.SleepUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

/**
 * 工作线程，相当于之前的消费者
 * 在消息手动应答时不丢失，放回队列中重新消费
 * @author gzw
 */
public class Worker022 {
    private static final String QUEUE_NAME = "ACK";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(30);
            System.out.printf("接收到的消息：%s\n", new String(message.getBody(), "UTF-8"));
            /**
             * ACK 应答参数的含义：
             *  1.消息的标记 tag
             *  2.是否应该批量应答，此处不应自动应答
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.printf("%s：消费者取消消费接口的逻辑", consumerTag);
        };

        // 接收消息，采用手动应答
        System.out.println("c2 等待接收消息的时间较长...");
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
