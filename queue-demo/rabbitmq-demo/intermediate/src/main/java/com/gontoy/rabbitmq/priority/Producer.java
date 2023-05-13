package com.gontoy.rabbitmq.priority;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gzw
 */
public class Producer {
    private static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        try (Channel channel = RabbitMqUtils.getChannel();) {
            // 给消息赋予一个 priority 属性
            AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().priority(5).build();
            // 设置优先级
            Map<String, Object> arguments = new HashMap<>();
            arguments.put("x-max-priority", 10);
            channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
            for (int i = 1; i < 11; i++) {
                String message = String.format("info: %s", i);
                if (i == 5) {
                    channel.basicPublish("", QUEUE_NAME, properties, message.getBytes());
                } else {
                    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                }
                System.out.printf("发送消息完成: %s\n", message);
            }
        }
    }
}
