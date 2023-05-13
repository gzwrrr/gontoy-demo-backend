package com.gontoy.rabbitmq.e_release_confirmation;

import com.gontoy.rabbitmq.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 发布确认模式
 *  1.单个确认发布：发布 1000 个单独确认消息，耗时：42600 ms、42154 ms
 *  2.批量确认：发布 1000 个批量确认消息，耗时：558 ms、526 ms
 *  3.异步批量确认：发布 1000 个异步确认消息，耗时：94 ms、99 ms
 * @author gzw
 */
public class ConfirmMessage {
    // 批量发送消息的个数
    public static final int MESSAGE_COUNT = 1000;

    public static void main(String[] args) throws Exception {
        // 单个确认
        // publishMessageIndividually();
        // 批量确认
        // publishMessageBatch();
        // 异步确认
        publishMessageAsync();
    }

    /**
     * 单个确认方法
     */
    public static void publishMessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明队列
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开始发布确认
        channel.confirmSelect();

        // 开始的时间
        long begin = System.currentTimeMillis();

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queueName, null, message.getBytes());
            // 单个确认
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("消息发送成功");
            }
        }

        // 结束的时间
        long end = System.currentTimeMillis();
        System.out.printf("发布 %d 个单独确认消息，耗时：%d ms", MESSAGE_COUNT, end - begin);
    }

    /**
     * 批量确认方法
     */
    public static void publishMessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明队列
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开始发布确认
        channel.confirmSelect();

        // 开始的时间
        long begin = System.currentTimeMillis();

        // 批量确认消息的大小
        int batchSize = 100;

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queueName, null, message.getBytes());
            // 判断达到 100 条的时候批量确认一次
            if (i % batchSize == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("批量确认一次");
                }
            }
        }

        // 结束的时间
        long end = System.currentTimeMillis();
        System.out.printf("发布 %d 个批量确认消息，耗时：%d ms", MESSAGE_COUNT, end - begin);
    }

    /**
     * 异步确认方法
     */
    public static void publishMessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明队列
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, false, false, false, null);
        // 开始发布确认
        channel.confirmSelect();

        /**
         * 线程安全有序的哈希表
         * 适用于高并发的情况
         *  1.可以轻松地将序号与消息进行关联
         *  2.可以轻松地删除条目
         *  3.支持多线程（高并发）
         */
        ConcurrentSkipListMap<Long, String> outstandingConfirms = new ConcurrentSkipListMap<>();

        // 开始的时间
        long begin = System.currentTimeMillis();

        // 发送消息成功的回调
        ConfirmCallback confirmCallback = (deliveryTag, multiple) -> {
            if (multiple) {
                System.out.printf("确认的消息：%s \n", deliveryTag);
                // 删除确认的信息，剩下的就是未确认的消息
                ConcurrentNavigableMap<Long, String> confirmed = outstandingConfirms.headMap(deliveryTag);
                confirmed.clear();
            } else {
                outstandingConfirms.remove(deliveryTag);
            }
        };
        // 发送消息失败的回调
        ConfirmCallback nackCallback = (deliveryTag, multiple) -> {
            // 打印未确认的信息
            String message = outstandingConfirms.get(deliveryTag);
            System.out.printf("未确认的消息：%s，tag 为：%s \n", message, deliveryTag);
        };

        // 消息监听器，监听那些消息成功或失败
        channel.addConfirmListener(confirmCallback, nackCallback);

        // 批量发消息
        for (int i = 0; i < MESSAGE_COUNT; i++) {
            String message = String.valueOf(i);
            channel.basicPublish("", queueName, null, message.getBytes());
            // 记录下要发送的全部信息
            outstandingConfirms.put(channel.getNextPublishSeqNo(), message);
        }

        // 结束的时间
        long end = System.currentTimeMillis();
        System.out.printf("发布 %d 个异步确认消息，耗时：%d ms", MESSAGE_COUNT, end - begin);
    }
}
