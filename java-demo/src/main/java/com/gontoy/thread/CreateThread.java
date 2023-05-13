package com.gontoy.thread;


import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建线程
 */
public class CreateThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 第一种方式 -- 继承 Thread
        Thread01 thread01 = new Thread01();
        thread01.start();

        // 第二种方法 -- 实现 Runable 接口
        Thread02 thread02 = new Thread02();
        new Thread(thread02).start();
        // Runnable 直接使用 Lambda 表达式
        new Thread(() -> {
            System.out.println("通过实现 Runnable 接口的方式创建线程（Lambda 表达式）");
        }).start();

        // 第三种方法 -- 实现 Callable 接口，配合 FutureTask
        Thread03 thread03 = new Thread03();
        FutureTask<Integer> futureTask1 = new FutureTask<Integer>(thread03);
        futureTask1.run();
        System.out.printf("Callable: %d %n", futureTask1.get());
        // Callable 直接使用 Lambda 表达式
        FutureTask<Integer> futureTask2 = new FutureTask<Integer>(() -> {
            System.out.println("通过实现 Callable 接口的方式创建线程（Lambda 表达式）");
            return 2;
        });
        futureTask2.run();
        System.out.printf("Callable: %d %n", futureTask2.get());

    }
}

class Thread01 extends Thread {
    @Override
    public void run() {
        System.out.println("通过继承的方式创建线程");
    }
}


class Thread02 implements Runnable {
    @Override
    public void run() {
        System.out.println("通过实现 Runnable 接口的方式创建线程");
    }
}


class Thread03 implements Callable {
    @Override
    public Object call() throws Exception {
        System.out.println("通过实现 Callable 接口的方式创建线程");
        return 1;
    }
}