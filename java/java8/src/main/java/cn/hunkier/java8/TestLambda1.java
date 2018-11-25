package cn.hunkier.java8;

import org.junit.Test;

import java.util.*;

/**
 * lambda 表达式
 */
public class TestLambda1 {

    /**
     * 原来的匿名内部类
     */
    @Test
    public void tests1() {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> ts = new TreeSet<>(com);

    }

    /**
     * Lambda 表达式
     */
    @Test
    public void test2(){
        Comparator<Integer> com = (x,y)->Integer.compare(x,y);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }



    List<Employee> employees = EmployeeUtils.getEmployees();


    public List<Employee> filterEmployees(List<Employee> employees){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() > 35){
                emps.add(employee);
            }
        }
        return  emps;

    }

    public List<Employee> filterEmployees2(List<Employee> employees){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getSalary() >= 5000){
                emps.add(employee);
            }
        }
        return  emps;

    }

    public List<Employee> filterEmployee(List<Employee> employees, MyPredicate<Employee> mp){
        List<Employee> emps = new ArrayList<>();
        for (Employee employee : employees) {
            if (mp.test(employee)){
                emps.add(employee);
            }
        }
        return  emps;
    }

    /**
     * 需求：获取当前公司中员工大于35岁的员工信息
     */
    @Test
    public void test3(){
        List<Employee> employees = filterEmployees(this.employees);
        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }

    /**
     * 需求：获取当前公司中员工工资大于5000的员工信息
     */
    @Test
    public void test4(){
        List<Employee> employees = filterEmployees(this.employees);
        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }


    /**
     * 优化方式一: 策略设计模式
     */
    @Test
    public  void test5(){
        List<Employee> employees = filterEmployee(this.employees, new FilterEmployeeByAge());
        for (Employee employee : employees) {
            System.out.println(employee);
        }

        System.out.println("-----------------------------------------------------------------");
        List<Employee> employees1 = filterEmployee(this.employees, new FilterEmployeeBySalary());
        for (Employee employee : employees1) {
            System.out.println(employee);
        }
    }


    /**
     *  优化方式二： 匿名内部类
     */
    @Test
    public  void test6(){
        List<Employee> employees = filterEmployee(this.employees, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getSalary() <= 5000;
            }
        });

        for (Employee employee : employees) {
            System.out.println(employee);
        }
    }

    /**
     * 优化方式三 ： Lambda表达式
     */
    @Test
    public void test7(){
        List<Employee> employees = filterEmployee(this.employees, (e) -> e.getSalary() >= 5000);
        employees.forEach(System.out::println);
    }

    /**
     * 优化方式四: Stream API
     */
    @Test
    public void test8(){
        employees.stream()
                .filter((employee)->employee.getSalary() >= 5000)
                .limit(2)
                .forEach(System.out::println);

        System.out.println("-----------------------------------------");
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);
    }






}
