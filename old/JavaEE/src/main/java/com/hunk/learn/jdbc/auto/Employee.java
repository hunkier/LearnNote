package com.hunk.learn.jdbc.auto;

/**
 * Created by hunk on 2015/8/11.
 */
public class Employee {
    private int empId;
    private String empName;
    // 关联部门
    private Dept dept;

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public Dept getDept() {
        return dept;
    }

    public void setDept(Dept dept) {
        this.dept = dept;
    }
}
