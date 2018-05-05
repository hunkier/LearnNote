package com.hunk.java8;

import org.junit.Test;

import java.util.Optional;

/**
 * 一、Optional 容器类：用于尽量避免空指针异常
 *      Optinal.of(T t) ： 创建一个 Optinal 实例
 *      Optinal.empty() :  创建一个空的Optinal实例
 *      Optinal.ofNullable(T t) :   若 t 不为null，创建Optinal实例，否则创建空实例
 *      isPresent() : 判断是否包含值
 *      orElse(T t) : 如果调用对象包含值，返回值，否则返回t
 *      orElseGet(supplier s) : 如果调用对象包含值，返回值，否则返回s获取的值
 *      map(Function f) : 如果有值对其处理，并返回处理后的Optinal，否则返回Optinal.empty
 *      flatMap(Function mapper) : 与map类似，要求返回必须是Optinal
 */
public class TestOptinal {

    @Test
    public void test1(){
        Optional<Employee> op = Optional.of(new Employee());
        Employee employee = op.get();
        System.out.println(employee);


    }

    @Test
    public void test2(){
        Optional<Employee> op = Optional.ofNullable(null);
        System.out.println(op.get());
        Optional<Employee> op2 = Optional.empty();
        System.out.println(op2.get());
    }

    @Test
    public void test3(){
//        Optional<Employee> op = Optional.ofNullable(new Employee());
        Optional<Employee> op = Optional.ofNullable(null);
        if (op.isPresent()){
            System.out.println(op.get());
        }

        Employee employee = op.orElse(new Employee("张三"));
        System.out.println(employee);

        Employee employee1 = op.orElseGet(() -> new Employee("李四"));
        System.out.println(employee1);

    }

    @Test
    public void test4(){
        Optional<Employee> op = Optional.of(new Employee("张三"));

        Optional<String> op2 = op.map(Employee::getName);
        System.out.println(op2.get());

        Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(op3.get());

    }

    @Test
    public void test5(){
        Man man = new Man();
        String godnessName = getGodnessName(man);
        System.out.println(godnessName);

        Optional<NewMan> newMan = Optional.ofNullable(null);
        String name2 = getGodnessName2(newMan);
        System.out.println(name2);
    }

    public String getGodnessName2(Optional<NewMan> optionalMan){
        return optionalMan.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("no body"))
                .getName();


    }

    public String getGodnessName(Man man){
        if (null!=man){
            Godness godness = man.getGodness();
            if (null!=godness){
                return godness.getName();
            }
        }
        return "no body";
    }
}
