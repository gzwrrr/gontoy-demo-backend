package com.gontoy.jvm;

import java.util.LinkedList;
import java.util.List;

/**
 * OOM 测试
 */
public class OomTest {
    public static void main(String[] args) {
        createObject();
    }

    /**
     * JVM 参数：-Xmx64M -XX:+HeapDumpOnOutOfMemoryError
     * 可以获得文件：java_pid23076.hprof
     */
    public static void createObject() {
        List<Integer> list = new LinkedList<>();
        while (true) {
            list.add(1);
        }
    }

}
