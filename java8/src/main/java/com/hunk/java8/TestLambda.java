package com.hunk.java8;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class TestLambda {


    List<Employee> employees = Arrays.asList(
            new Employee("张三", 18, 9999.99),
            new Employee("李四", 38, 5555.55),
            new Employee("王五", 50, 6666.66),
            new Employee("赵六", 16, 3333.33),
            new Employee("田七", 8, 7777.77)
    );


    @Test
    public void test1(){
        Collections.sort(employees, (x, y) -> {
            int compare = Integer.compare(x.getAge(), y.getAge());
            if (0==compare){
                return  x.getName().compareTo(y.getName());
            }
            return  compare;
        });
        employees.stream().forEach(System.out::println);
        System.out.println("-----------------------------");
        employees.stream()
                .sorted((x,y)->Double.compare(x.getSalary(),y.getSalary()))
                .forEach(System.out::println);
    }

    /**
     * 处理字符串
     */
    @Test
    public void test2(){
        String upperCase = strHandler("Hello Lambda!", (str -> str.toUpperCase()));
        System.out.println(upperCase);
    }

    public String strHandler(String str, MyFunction mf){
        return  mf.getValue(str);
    }

    /**
     * 对两个数字进行运算
     */
    @Test
    public  void test3(){
        op(100L,100L,(x,y)->x+y);

        op(200L,200L, (x,y)->x*y);
    }

    public void op(Long l1, Long l2, MyFunction2<Long, Long> mf){
        System.out.println(mf.getValue(l1,l2));
    }

}
