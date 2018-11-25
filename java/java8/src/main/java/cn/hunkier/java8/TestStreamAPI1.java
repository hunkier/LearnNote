package cn.hunkier.java8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * 一、Stream 的三个操作步骤
 *
 *      1. 创建Stream
 *
 *      2. 中间操作
 *
 *      3. 终止操作（终端操作）
 *
 *
 *
 *
 *
 */
public class TestStreamAPI1 {


    /**
     * 创建 Stream
     */
    @Test
    public void test1(){

        // 1. 可以通过Collection系列集合提供的 stream() 或 paralleStream
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream();


        // 2. 通过 Arrays 中的静态方法 stream() 获取数组流
        Employee[] employees = new Employee[10];
        Stream<Employee> stream1 = Arrays.stream(employees);

        // 3. 通过 Stream 类中的静态方法 of()
        Stream<String> stream2 = Stream.of("Hello", "Lambda", "Stream", "API");

        // 4. 创建无限流
        // 迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2);
        stream3.limit(10).forEach(System.out::println);

        // 生成
        Stream<Double> generate = Stream.generate(() -> Math.random());
        generate.limit(10).forEach(System.out::println);


    }
}
