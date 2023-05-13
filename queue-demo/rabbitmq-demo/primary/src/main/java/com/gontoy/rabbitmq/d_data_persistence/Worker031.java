package com.gontoy.rabbitmq.d_data_persistence;

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
public class Worker031 {
    private static final String QUEUE_NAME = "ACK";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            SleepUtils.sleep(1);
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

        // 设置不公平分发
        int preFetchCount = 1;
        channel.basicQos(preFetchCount);
        // 接收消息，采用手动应答
        System.out.println("c1 等待接收消息的时间较短...");
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, cancelCallback);
    }
}
