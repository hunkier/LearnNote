package cn.hunkier.java8;

import java.util.Arrays;
import java.util.List;

public class EmployeeUtils {

    public static List<Employee> getEmployees(){
        return  Arrays.asList(
                new Employee("张三", 18, 9999.99, Employee.Status.FREE),
                new Employee("李四", 38, 5555.55, Employee.Status.BUSY),
                new Employee("王五", 50, 6666.66, Employee.Status.VOCATION),
                new Employee("赵六", 16, 3333.33, Employee.Status.FREE),
                new Employee("田七", 8, 7777.77, Employee.Status.BUSY)
        );
    }
}
