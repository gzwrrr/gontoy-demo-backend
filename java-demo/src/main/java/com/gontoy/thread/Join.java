package com.gontoy.thread;


/**
 * join 方法
 */
public class Join {
    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> System.out.println("子线程 1 执行"));
        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("子线 2 程执行");
        });

        thread2.start();
        thread1.start();

        try {
            //主线程开始等待子线程thread1，thread2
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //等待两个线程都执行完（不活动）了，才执行下行打印
        System.out.println("执行完毕");
    }

}
