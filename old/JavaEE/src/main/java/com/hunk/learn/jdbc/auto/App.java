package com.hunk.learn.jdbc.auto;

import org.junit.Test;

/**
 * Created by hunk on 2015/8/11.
 */
public class App {
    /**
     * 保存员工
     */
    @Test
    public void testSave(){
        // 模拟数据
        Dept d = new Dept();
        d.setDeptName("应用开发部");
        Employee emp = new Employee();
        emp.setEmpName("李连杰");
        emp.setDept(d);// 关联
        // 调用dao保存
        EmpDao empDao = new EmpDao();
        empDao.save(emp);
    }
}
