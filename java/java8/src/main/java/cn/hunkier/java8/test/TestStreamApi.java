package cn.hunkier.java8.test;

import cn.hunkier.java8.Employee;
import cn.hunkier.java8.EmployeeUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class TestStreamApi {

    /**
     *
     *      1. 给定一个数字列表，如何返回一个由每个数的平方构成的列表呢？
     *          给定 【1, 2, 3, 4, 5】，应该返回【1, 4, 9, 16, 25】
     */
    @Test
    public void test1(){
        Integer[] nums = {1, 2, 3, 4, 5};

        Stream<Integer> stream = Arrays.asList(nums)
                .stream()
                .map((x) -> x * x);
        stream.forEach(System.out::println);
    }

    List<Employee> employees = EmployeeUtils.getEmployees();

    /**
     *      2. 采用 map-reduce计算员工数量
     */
    @Test
    public void test2(){
        Optional<Integer> reduce = employees.stream()
                .map((e) -> 1)
                .reduce(Integer::sum);
        System.out.println(reduce.get());

    }

}
