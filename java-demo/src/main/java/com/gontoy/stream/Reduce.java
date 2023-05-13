package com.gontoy.stream;


import java.util.Arrays;
import java.util.List;

/**
 * 相关文章：https://blog.csdn.net/lijingronghcit/article/details/108348728
 *
 * Identity : 定义一个元素代表是归并操作的初始值，如果Stream 是空的，也是Stream 的默认结果
 * Accumulator: 定义一个带两个参数的函数，第一个参数是上个归并函数的返回值，第二个是Strem 中下一个元素。
 * Combiner: 调用一个函数来组合归并操作的结果，当归并是并行执行或者当累加器的函数和累加器的实现类型不匹配时才会调用此函数。
 */
public class Reduce {

    public static void main(String[] args) {
        Reduce reduce = new Reduce();
        reduce.identity();

        System.out.println();
        reduce.accumulator();

        System.out.println();
        reduce.combiner();
    }

    /**
     * Identity : 定义一个元素代表是归并操作的初始值，如果Stream 是空的，也是Stream 的默认结果
     * reduce 方法的第一个参数 0 是 identity ，此参数用来保存归并参数的初始值，当Stream 为空时也是默认的返回值。
     * (subtotal, element) -> subtotal + element 是accumulator ，第一个参数是上次累计的和，第二个参数是数据流的下一个元素。
     */
    public void identity() {
        List<Integer> numbers1 = Arrays.asList();
        int result1 = numbers1
                .stream()
                .reduce(0, (subtotal, element) -> subtotal + element);
        System.out.println(result1);
    }


    /**
     * Accumulator: 定义一个带两个参数的函数，第一个参数是上个归并函数的返回值，第二个是Strem 中下一个元素。
     * 可以直接传入求和方法：Integer::sum
     */
    public void accumulator() {
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        int result1 = numbers2.stream().reduce(0, Integer::sum);
        System.out.println(result1);

        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result2 = letters
                .stream()
                .reduce("", String::concat);
        System.out.println(result2);
    }


    /**
     * Combiner: 调用一个函数来组合归并操作的结果，当归并是并行执行或者当累加器的函数和累加器的实现类型不匹配时才会调用此函数。
     *
     */
    public void combiner() {
        List<User> users = Arrays.asList(new User("John", 30), new User("Julie", 35));
        // 下面不加 Combiner 会报错，因为 User 和 age 一个是对象一个是整型，不匹配时无法推断，必须告诉它可以直接累加
        // int computedAges =
        //         users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge());
        int computedAges1 =
                users.stream().reduce(0, (partialAgeResult, user) -> partialAgeResult + user.getAge(), Integer::sum);
        System.out.println(computedAges1);


        // 可以使用并行流合并，需要指定 Combiner
        List<Integer> ages = Arrays.asList(25, 30, 45, 28, 32);
        int computedAges2 = ages.parallelStream().reduce(0, (a, b) -> a + b, Integer::sum);
        System.out.println(computedAges2);
    }

}


class User {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}