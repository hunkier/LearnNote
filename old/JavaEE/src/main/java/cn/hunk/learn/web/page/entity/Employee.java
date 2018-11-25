package cn.hunk.learn.web.page.entity;

/**
 * 1.实体类设计(因为用了DbUtils组件，属性要与数据库中字段一致)
 * Created by hunkier on 15/8/12.
 */
public class Employee {
    private int empId; // 员工id
    private String empName; // 员工名称
    private int deptId; // 部门id

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

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", deptId=" + deptId +
                '}';
    }
}
