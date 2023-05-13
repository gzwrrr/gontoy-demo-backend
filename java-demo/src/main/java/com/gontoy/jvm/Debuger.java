package com.gontoy.jvm;

import java.util.LinkedList;
import java.util.List;

public class Debuger {

    public static void main(String[] args) {
        f();
    }

    public static void f() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
    }
}
