package com.gontoy.jvm;

public class Jps {

    public static void main(String[] args) {
        loop();
    }

    public static void loop() {
        while (true) {
            try {
                System.out.println("Running...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
