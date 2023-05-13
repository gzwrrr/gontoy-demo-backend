package com.gontoy.rabbitmq.g_dead_letter_queue;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;

/**
 * 死信队列
 * 普通消费者
 * 当发生以下情况时，消息进入死信队列：
 *  1.消息被拒绝
 *  2.消息 TTL 过期
 *  3.队列达到最大长度
 * @author gzw
 */
public class Consumer01 {
    // 普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    // 死信交换机的名称
    public static final String DEAD_EXCHANGE = "dead_exchange";

    // 普通队列的名称
    public static final String NORMAL_QUEUE = "normal_queue";

    // 死信队列的名称
    public static final String DEAD_QUEUE = "dead_queue";


    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        Map<String, Object> arguments = new Hashtable<>();
        // 设置过期时间，也可以不设置，因为一般是由生产者设置的
        // arguments.put("x-message-ttl", 100000);
        // 正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        // 设置死信的 RoutingKey
        arguments.put("x-dead-letter-routing-key", "dead-routing");
        // 设置正常队列的长度的限制
        arguments.put("x-max-length", 6);

        // 声明死信和普通交换机，类型为 direct
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        // 声明普通队列，记得加上上面设置的参数
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);

        // 声明死信队列
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 绑定普通的交换机和队列
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal-routing");

        // 绑定死信的交换机和队列
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead-routing");

        DeliverCallback deliverCallback = (consumerTag, message) -> {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            // 模拟消息被拒绝
            if (msg.equals("info：5")) {
                System.out.println("拒绝消息");
                channel.basicReject(message.getEnvelope().getDeliveryTag(), false);
            } else {
                System.out.printf("Consumer01 接收的消息是：%s\n", msg);
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }
        };

        // 记得开启手动应答，否则无法决绝消息
        channel.basicConsume(NORMAL_QUEUE, false, deliverCallback, consumerTag -> {});
    }
}
