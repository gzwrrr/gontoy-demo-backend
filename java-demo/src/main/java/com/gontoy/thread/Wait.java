package com.gontoy.thread;

/**
 * wait 方法
 * wait 方法会释放锁，sleep 方法不会释放锁
 */
public class Wait {
    public static void main(String[] args) {

        Print print = new Print();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printNum();
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 26; i++) {
                print.printChar();
            }
        }).start();




    }
}


class Print {
    //信号量。当值为1时打印数字，当值为2时打印字母
    private int flag = 1;
    private int count = 1;

    public synchronized void printNum() {
        if (flag != 1) {
            //打印数字
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("A: %d, %d %n", 2 * count - 1, 2 * count);
        flag = 2;
        notify();
    }

    public synchronized void printChar() {
        if (flag != 2) {
            //打印字母
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.printf("B: %c %n", (char) (count - 1 + 'A'));
        // 当一轮循环打印完之后，计数器加1
        count++;
        flag = 1;
        notify();
    }
}