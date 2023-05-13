package com.gontoy.rabbitmq.f_exchange.c_topic;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;

/**
 * 生产者
 * @author gzw
 */
public class EmitLogTopic {
    // 交换机的名称
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // map 绑定交换机
        Map<String, String> bindingKeyMap = new Hashtable<>();
        bindingKeyMap.put("quick.orange.rabbit", "被第 1、2个队列接收到");
        bindingKeyMap.put("quick.orange.fox", "被第 1 个队列接收到");
        bindingKeyMap.put("lazy.orange.rabbit.fox", "被第 2 个队列接收到");

        for (Map.Entry<String, String> bindingKeyEntry : bindingKeyMap.entrySet()) {
            String routingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.printf("生产者发出消息：%s\n", message);
        }
    }
}
