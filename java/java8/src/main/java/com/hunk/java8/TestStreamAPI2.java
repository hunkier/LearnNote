package com.hunk.java8;


import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
public class TestStreamAPI2 {


    // 中间操作

    /**
     * 排序
     * sorted()——自然排序( Comparable)
     * sorted(Comparator com)——定制排序
     */
    @Test
    public void test7(){
        List<String> list = Arrays.asList("cccc", "eeee", "bbbb", "ddddd", "aaaa");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("---------------------------------------");

        List<Employee> employees = EmployeeUtils.getEmployees();
        employees.stream()
                .sorted((e1,e2)->{
                    return Integer.compare(e1.getAge(),e2.getAge())==0? e1.getName().compareTo(e2.getName()) : -Integer.compare(e1.getAge(),e2.getAge());
                })
                .forEach(System.out::println);

    }

    /**
     *
     * 映射
     * map——接收 Lambda ，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每一个元素上，并将其映射成一个新的元素。
     * flatMap——接收一个函数作为参数，将流中的每一个值都换成另一个流，然后把所有流链接成一个流
     */

    @Test
    public void test6(){
        List<String> list = Arrays.asList("Hello", "Lambda!");
        list.stream()
                .map((str)->str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("--------------------------------------------");

        EmployeeUtils.getEmployees()
                .stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("--------------------------------------------");

        Stream<Stream<Character>> stream = list.stream()
                .map(TestStreamAPI2::filterCharacter);
        stream.forEach((sm)->{
            sm.forEach(System.out::println);
        });

        System.out.println("---------------------------------------------");

        Stream<Character> sm = list.stream()
                .flatMap(TestStreamAPI2::filterCharacter);

        sm.forEach(System.out::println);


    }


    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }

    /**
     *      筛选与切片
     *      filter——接收Lambda , 从流中排除某些元素
     *      limit——截断流，使其元素不超过给定数量
     *      skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中不足 n 个，则饭回一个空流。与 limit 互补
     *      distinct —— 筛选，通过流所生成元素的hashCode() 和 equals() 去除重复元素
     */

    @Test
    public void test5(){
        EmployeeUtils.getEmployees()
                .stream()
                .filter((e)-> e.getSalary()>5000)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    public void test4(){
        EmployeeUtils.getEmployees()
                .stream()
                .filter((e)-> e.getSalary()>5000)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test3(){
        EmployeeUtils
                .getEmployees()
                .stream()
                .filter((e)->{
                    System.out.println("短路！");
                    return  e.getSalary() > 5000;
                })
                .limit(2)
                .forEach(System.out::println);

    }

    /**
     * 内部迭代：迭代操作由 Stream API 完成
     */
    @Test
    public void test1(){
        // 中间操作：不会执行任何操作
        Stream<Employee> stream = EmployeeUtils.getEmployees().stream()
                .filter((e) -> {
                    System.out.println("Stream API 的中间操作");
                    return e.getAge() > 35;
                });

        // 终止操作：一次性执行全部内容，既“惰性求值”
        stream.forEach(System.out::println);
    }

    /**
     * 外部迭代
     */
    @Test
    public void test2(){
        Iterator<Employee> iterator = EmployeeUtils.getEmployees().iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
