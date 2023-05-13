package com.gontoy.rabbitmq.utils;

/**
 * 用于休眠的工具类
 * @author gzw
 */
public class SleepUtils {
    public static void sleep(int second) {
        try {
            Thread.sleep(1000 * second);
        } catch (InterruptedException _ignore) {
            Thread.currentThread().interrupt();
        }
    }
}
