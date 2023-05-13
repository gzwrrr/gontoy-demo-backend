package com.gontoy.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 获取 stream 的方法
 */
public class GetStream {

    public static void main(String[] args) {
        getListStream(new ArrayList());
        getSetStream(new HashSet());
        getMapStream(new HashMap());
        getArrayStream(new int[]{});
    }

    /**
     * 获取 List 的 stream
     * @param list
     * @return
     */
    public static void getListStream(List list) {
        System.out.println(list.stream());
    }


    /**
     * 获取 Set 的 stream
     * @param set
     * @return
     */
    public static void getSetStream(Set set) {
        System.out.println(set.stream());
    }


    /**
     * 获取 Map 的 stream
     * @param map
     * @return
     */
    public static void getMapStream(Map map) {
        System.out.println(map.keySet().stream().collect(Collectors.toSet()));
        System.out.println(map.values().stream());
        System.out.println(map.entrySet().stream());
    }


    public static void getArrayStream(int[] array) {
        System.out.println(Stream.of(array));
        System.out.println(Stream.of(1, 2, 3, 4));
        System.out.println(Stream.of("1", "2", "3","4"));
    }

}
