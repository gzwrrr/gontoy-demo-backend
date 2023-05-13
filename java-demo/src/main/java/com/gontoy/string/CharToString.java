package com.gontoy.string;

public class CharToString {
    public static void main(String[] args) {
        char[] ch = {'a', 'b'};
        // 这个是打印对象
        System.out.println(ch.toString());
        // 这个是转换成字符串
        System.out.println(new String(ch));
    }
}


