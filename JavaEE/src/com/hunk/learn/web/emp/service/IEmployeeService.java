package com.hunk.learn.web.emp.service;

import com.hunk.learn.web.emp.entity.Employee;

import java.util.List;

/**
 * 2.员工业务逻辑层
 * Created by hunk on 2015/8/13.
 */
public interface IEmployeeService {
    /**
     * 查询所有的员工
     * @return
     */
    public List<Employee> getAll();
}
