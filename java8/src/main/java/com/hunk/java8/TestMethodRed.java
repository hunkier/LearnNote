package com.hunk.java8;

import org.junit.Test;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.*;

/**
 * 一、方法引用：若Lambda体中的内容有方法已经实现，我们可以使用“方法 引用”
 *         （可以理解方法引用是Lambda表达式的另外一种 表现形式）
 *
 * 主要有三种语法格式：
 *
 * 对象::实例方法名
 *
 * 类::静态方法名
 *
 * 类::实例方法名
 *
 * 注意：
 *      ①Lambda 体 中调用方法的参数列表与返回值的类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致！
 *      ②若Lambda 参数列表中的第一个参数是 实例方法的调用者，而第二个参数是实例方法参数时，可以使用ClassName::method
 *
 * 二、构造器引用
 *
 *    格式：
 *
 *    ClassNmae::new
 *
 *    注意：需要调用的构造器函数的参数列表与函数式接口中的抽象方法的参数列表保持一致！
 *
 * 三、数组引用
 *
 *      Type[]::new;
 *
 *
 */
public class TestMethodRed {


    /**
     * 数组引用
     */
    @Test
    public void  test7(){
        Function<Integer,String[]> fun = (x) -> new String[x];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);

        Function<Integer, String[]> fun2 = String[]::new;
        String[] strings = fun2.apply(100);
        System.out.println(strings.length);

    }

    /**
     * 带参数的构造器引用
     */
    @Test
    public void test6(){
        Function<Integer,Employee> fun = (x) -> new Employee(x);

        Function<Integer,Employee> fun2 = Employee::new;
        Employee employee = fun2.apply(1010);
        System.out.println(employee);

        BiFunction<Integer,Integer, Employee> fun3 = Employee::new;
        Employee e = fun3.apply(1001, 28);
        System.out.println(e);
    }


    /**
     * 构造器引用
     */
    @Test
    public void test5(){
        Supplier<Employee> sup = ()-> new Employee();

        // 构造器引用方式
        Supplier<Employee> sup2 = Employee::new;

        Employee employee = sup2.get();
        System.out.println(employee);

    }

    /**
     * 类::实例方法名
     */
    @Test
    public void test4(){

        BiPredicate<String,String> bp = (x, y) -> x.equals(y);

        BiPredicate<String, String> bp2 = String::equals;

        boolean hello = bp2.test("Hello", "Lambda!");
        System.out.println(hello);

    }


    /**
     * 类::静态方法名
     */
    @Test
    public void test3(){
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);

        Comparator<Integer> com1 = Integer::compare;


    }

    /**
     * 对象::实例方法名
     */
    @Test
    public void test1(){
        PrintStream ps1 = System.out;

        Consumer<String> con = (x) -> ps1.println(x);

        PrintStream ps = System.out;
        Consumer<String> con1 = ps::println;

        Consumer<String> con2 = System.out::println;
        con2.accept("Hello Lambda!");
    }


    @Test
    public void test2(){
        Employee employee = new Employee("hunk", 28, 25000);
        Supplier<String> sup = ()-> employee.getName();
        String name = sup.get();
        System.out.println(name);


        Supplier<Integer> sup2 = employee::getAge;
        Integer age = sup2.get();
        System.out.println(age);

    }
}
