package com.gontoy.stream;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 流的常用方法
 *
 * 方法名称	方法作用	  方法种类	是否支持链式调用
 * count	统计个数	  终结方法	否
 * forEach	逐一处理	  终结方法	否
 *
 * filter	过滤	      函数拼接	是
 * limit	取用前几个  函数拼接	是
 * skip	    跳过前几个  函数拼接	是
 * map	    映射	      函数拼接	是
 * concat	组合	      函数拼接	是
 * sorted	排序	      函数拼接	是
 *
 * 注意：stream 只能被使用一次
 */
public class Method {
    public static void main(String[] args) {
        System.out.println("计数");
        count();

        System.out.println("遍历");
        forEach();

        System.out.println("过滤");
        filter();

        System.out.println("取前 n 个");
        limit();

        System.out.println("跳过前 n 个");
        skip();

        System.out.println("元素映射");
        map();

        System.out.println("元素拼接");
        concat();

        System.out.println("排序");
        sorted();
    }

    /**
     * 统计个数
     * 终结方法
     */
    public static void count() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4);
        System.out.println(stream.count());
    }


    /**
     * 遍历
     * 终结方法
     */
    public static void forEach() {
        Stream stream = Stream.of(1, 2, 3, 4);
        stream.forEach((item) -> {
            System.out.println(item);
        });
    }

    /**
     * 过滤
     * 链式调用
     */
    public static void filter() {
        Stream stream = Stream.of(1, 2, 3, 4);
        stream.filter((item) -> item.equals(2) || item.equals(3))
                .forEach((item) -> {
            System.out.println(item);
        });
    }


    /**
     * 取前 n 个
     * 链式调用
     */
    public static void limit() {
        Stream stream = Stream.of(1, 2, 3, 4);
        System.out.println(stream.limit(2).collect(Collectors.toList()));
    }

    /**
     * 跳过前 n 个
     * 链式调用
     */
    public static void skip() {
        Stream stream = Stream.of(1, 2, 3, 4);
        System.out.println(stream.skip(3).collect(Collectors.toList()));
    }

    /**
     * 元素映射
     * 链式调用
     */
    public static void map() {
        Stream stream = Stream.of(1, 2, 3, 4);
        System.out.println(stream.map((item) -> item.equals(1)).collect(Collectors.toList()));
    }

    /**
     * 元素拼接
     * 静态方法
     * 链式调用
     */
    public static void concat() {
        Stream stream1 = Stream.of(1, 2);
        Stream stream2 = Stream.of(3, 4);
        System.out.println(Stream.concat(stream1, stream2).collect(Collectors.toList()));
    }

    /**
     * 排序
     * 链式调用
     */
    public static void sorted() {
        Stream stream = Stream.of(4, 3, 1, 2);
        System.out.println(stream.sorted().collect(Collectors.toList()));
    }
}
